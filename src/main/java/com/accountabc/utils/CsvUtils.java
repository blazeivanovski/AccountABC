package com.accountabc.utils;

import com.accountabc.model.Security;
import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvUtils {
    public static List<Security> readCsv(String fileName) {
        List<Security> securities = new ArrayList<>();

        InputStream is = CsvUtils.class.getClassLoader().getResourceAsStream(fileName);

        if (is == null) {
            throw new RuntimeException("CSV file not found in resources: " + fileName);
        }

        try (CSVReader reader = new CSVReader(new InputStreamReader(is))) {

            String[] line;
            reader.readNext();

            while ((line = reader.readNext()) != null) {
                Security security = new Security();
                security.setName(line[0]);
                security.setTarget(Integer.parseInt(line[1]));
                security.setCurrent(Integer.parseInt(line[2]));
                security.setTargetVariance(Integer.parseInt(line[3]));
                security.setUnitPrice(Double.parseDouble(line[4]));
                securities.add(security);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return securities;
    }
}
