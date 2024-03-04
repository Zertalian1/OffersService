package com.example.service;

import com.example.domain.entity.*;
import com.example.repository.ClientRepository;
import com.example.repository.OfferPatternRepository;
import com.example.repository.OfferRepository;
import com.example.repository.ParameterRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final ClientRepository clientRepository;
    private final OfferPatternRepository offerPatternRepository;
    private final ParameterRepository parameterRepository;

    private final Logger log = LoggerFactory.getLogger(OfferService.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(20);
    @Value("${application.client.page-size}")
    private int pageSize;
    @Value("${application.samples.path}")
    private String reportsPath;

    private Map<String, Object> getValues(OfferPattern pattern, Client client) {
        Map<String, Object> params = new HashMap<>();
        for (Parameter parameter : parameterRepository.getParameterByOfferPatternId(pattern.getId())) {
            switch (parameter.getDataType().substring(parameter.getDataType().lastIndexOf('.') + 1)) {
                case "String" -> {
                    switch (parameter.getName()) {
                        case "firstName" -> params.put("firstName", client.getFirstName());
                        case "lastName" -> params.put("lastName", client.getLastName());
                        case "patronymic" -> params.put("patronymic", client.getPatronymic());
                        default -> params.put(parameter.getName(), "sampleText");
                    }
                }
                case "Integer" -> params.put(parameter.getName(), 10);
                case "Long" -> params.put(parameter.getName(), 20L);
                case "Double" -> params.put(parameter.getName(), 30.3);
                case "Float" -> params.put(parameter.getName(), 40.4f);
            }
        }
        return params;
    }

    private Offer generateReport(OfferPattern pattern, Client client) {
        StringBuilder builder = new StringBuilder();
        builder.append(reportsPath);
        builder.append(pattern.getDirectory().replace(".", "/"));
        builder.append("/");
        builder.append(pattern.getName());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            File file = ResourceUtils.getFile(builder.toString());
            JasperReport jasperReport = JasperCompileManager.compileReport(String.valueOf(file.getAbsoluteFile()));
            Map<String, Object> parameters = getValues(pattern, client);
            System.out.println(file.getAbsoluteFile());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            JRTextExporter exporter = new JRTextExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleWriterExporterOutput(outputStream));
            exporter.exportReport();
            System.out.println(outputStream.toString(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            log.warn("can not find pattern");
        } catch (JRException e) {
            log.warn("can not compile pattern");
        }
        Offer offer = new Offer();
        offer.setClient(client);
        offer.setPattern(pattern);
        offer.setOffer(outputStream.toByteArray());
        offer.setShowDate(LocalDate.now());
        return offer;
    }

    public void generateOfferForUser(Client client) {
        executorService.submit(() ->
                {
                    List<OfferPattern> patterns = offerPatternRepository
                            .selectPatternsByConditionsAndByUserId(client.getId());
                    List<Offer> offers = patterns.stream()
                            .map(offer -> generateReport(offer, client)).toList();
                    offerRepository.saveAll(offers);
                }
        );
    }

    @Scheduled(cron = "@monthly")
    private void generateOffers() {
        List<Client> clients;
        int offset = 0;
        offerRepository.deleteOffersByShowDateMoreSixMonth();
        do {
            clients = clientRepository.getClientByPage(offset * pageSize, pageSize);
            for (Client client : clients) {
                generateOfferForUser(client);
            }
            offset++;
        } while (!clients.isEmpty());

    }

}
