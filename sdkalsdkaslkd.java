 @Override
    public ResponseEntity<Resource> printAndDownloadReceipt(UUID id) {
        qReceipt receipt = receiptRepository.findById(id).orElse(null);
        if (isNull(receipt)) {
            throw NotFoundException.entityNotFoundById("qReceipt", id.toString());
        }
        String fileName = "receipt-" + LocalDate.now().toString() + ".pdf";
        byte[] result = printReceipt(receipt);
        ByteArrayResource resource = new ByteArrayResource(result);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(result.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
    @SneakyThrows
    private byte[] printReceipt(qReceipt receipt) {
        ReceiptForm form = receiptFormRepository.findById(1L).orElse(null);
        if (isNull(form)) {
            throw ServerException.noReceiptFormIsPresent();
        }
        List<JasperPrint> printList = new ArrayList<>();

        for (ReceiptTemplate template : form.getTemplates()) {
            Map<String, Object> parameters = fillParameters(template, receipt);

            JasperReport report = JasperCompileManager.compileReport(getJrxml(template));
            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
            printList.add(print);
        }
        byte[] result = getReceiptPages(printList);
        return result;
    }


    private Map<String, Object> fillParameters(ReceiptTemplate template, qReceipt receipt) {

        Map<String, Object> parameters = new HashMap<>();

        for (String param : template.getParamNames()) {

            switch (param) {
                case "date":
                    parameters.put(param, receipt.getCreatedDate().format(formatter));
                    break;
                case "total":
                    parameters.put(param, receipt.getTotalSum().toString());
                    break;
                case "main":
                    int cnt = 1;
                    List<ReceiptPrintTableDto> mainInfo = new ArrayList<>();

                    for (ReceiptCreateFieldDto entry : receipt.getJson()) {
                        mainInfo.add(new ReceiptPrintTableDto(
                                String.valueOf(cnt),
                                entry.getName(),
                                entry.getPrice().toString(),
                                entry.getQuantity().toString(),
                                entry.getTotalPrice().toString()
                                ));
                        cnt++;
                    }
                    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(mainInfo);
                    parameters.put(param, dataSource);
                    break;
            }

        }
        return parameters;
    }

    private InputStream getJrxml(ReceiptTemplate template) {
        return new ByteArrayInputStream(template.getValue().getBytes(StandardCharsets.UTF_8));
    }

    @SneakyThrows
    private byte[] getReceiptPages(List<JasperPrint> printList) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, printList);
        exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
        exporter.exportReport();
        return os.toByteArray();
    }

    private Double getTotalSum(List<ReceiptCreateFieldDto> products) {
        return products.stream().mapToDouble(ReceiptCreateFieldDto::getTotalPrice).sum();
    }
}
