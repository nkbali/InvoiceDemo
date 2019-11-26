package com.demo.invoice.controllers;

import com.demo.invoice.data.entities.Invoice;
import com.demo.invoice.data.repositories.InvoiceRepository;
import com.demo.invoice.models.Error;
import com.demo.invoice.models.InvoiceDTO;
import com.demo.invoice.models.Response;
import com.demo.invoice.models.Result;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

@RestController
@RequestMapping("invoices")
public class InvoiceController {

    InvoiceRepository invoiceRepository;

    ModelMapper modelMapper;

    Logger logger;

    Executor executor;

    @Autowired
    public InvoiceController(InvoiceRepository invoiceRepository, ModelMapper modelMapper, Logger logger, Executor executor){
        this.invoiceRepository = invoiceRepository;
        this.modelMapper = modelMapper;
        this.logger = logger;
        this.executor = executor;
    }

    @GetMapping
    public Response getInvoices() {

        Response response = new Response();

        TestCallable callable1 = new TestCallable();
        TestCallable callable2 = new TestCallable();

        FutureTask<String> futureTask1 = new FutureTask<String>(callable1);
        FutureTask<String> futureTask2 = new FutureTask<String>(callable2);

        executor.execute(futureTask1);
        executor.execute(futureTask2);

        try {
            List<InvoiceDTO> invoiceDTOS = new ArrayList<>();
            logger.info("Fetching Invoices");
            List<Invoice> invoicesList = invoiceRepository.findAll();
            invoiceDTOS = invoicesList.stream().map(invoice -> modelMapper.map(invoice, InvoiceDTO.class)).collect(Collectors.toList());

            Result result = new Result();
            result.setInvoices(invoiceDTOS);

            response.setResult(result);
            response.setResponseCode(200);
        } catch (Exception ex) {
            Error error = new Error();
            response.setResponseCode(500);
            error.setErrorMessage(ex.getMessage());
            response.setError(error);

            logger.error(ex.getClass().getSimpleName(), ex);
        }

        return response;
    }

    @GetMapping("{id}")
    public Response getInvoiceById(@PathVariable long id) {

        Response response = new Response();

        try {

            List<InvoiceDTO> invoiceDTOS = new ArrayList<>();
            logger.info("Fetching Invoice by id::" + id);
            Optional<Invoice> foundInvoice = invoiceRepository.findById(id);

            if (!foundInvoice.isPresent()) {
                Error error = new Error();
                response.setResponseCode(404);
                error.setErrorMessage("Not Found");

                response.setError(error);

                return response;
            }

            invoiceDTOS.add(modelMapper.map(foundInvoice.get(), InvoiceDTO.class));


            Result result = new Result();
            result.setInvoices(invoiceDTOS);

            response.setResult(result);
            response.setResponseCode(200);
        } catch (Exception ex) {
            Error error = new Error();
            response.setResponseCode(500);
            error.setErrorMessage(ex.getMessage());
            response.setError(error);

            logger.error(ex.getClass().getSimpleName(), ex);
        }

        return response;
    }

    @PostMapping
    public Response addInvoice(@RequestBody InvoiceDTO invoiceDTO){
        Response response = new Response();
        try{
           Invoice invoice =  modelMapper.map(invoiceDTO, Invoice.class);
           logger.info("Saving Invoice");
           invoice = invoiceRepository.save(invoice);
           logger.info("Invoice saved with id::" + invoice.getId());
           Result result = new Result();
           List<InvoiceDTO> invoiceDTOS = new ArrayList<>();

           invoiceDTOS.add(modelMapper.map(invoice, InvoiceDTO.class));
           result.setInvoices(invoiceDTOS);

           response.setResult(result);
            response.setResponseCode(201);

        } catch (Exception ex) {
            Error error = new Error();
            response.setResponseCode(500);
            error.setErrorMessage(ex.getMessage());
            response.setError(error);

            logger.error(ex.getClass().getSimpleName(), ex);
        }

        return response;
    }

}
