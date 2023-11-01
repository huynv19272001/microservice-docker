package com.lpb.esb.etax.payment.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.etax.payment.model.data.FeeCategoryElement;
import com.lpb.esb.etax.payment.model.data.ReceiptDetail;
import com.lpb.esb.etax.payment.model.data.TaxFee;
import com.lpb.esb.etax.payment.model.entity.EtaxPaymentInfo;
import com.lpb.esb.etax.payment.model.entity.EtaxReceiptInfo;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by tudv1 on 2022-06-21
 */
@Component
@Log4j2
public class DocxTemplateUtils {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    LogicUtils logicUtils;

    public void fillTemplateReceipt(EtaxReceiptInfo etaxReceiptInfo, EtaxPaymentInfo etaxPaymentInfo, String inputFile, String outputFile) {
        try (XWPFDocument doc = new XWPFDocument(
            Files.newInputStream(Paths.get(inputFile)))
        ) {
//            List<ReceiptDetail> details = objectMapper.readValue(etaxReceiptInfo.getTaxReceiptDetails(), new TypeReference<List<ReceiptDetail>>() {
//            });
//            long totalMoney = 0l;
//            for (ReceiptDetail detail : details) {
//                totalMoney += detail.getSoTien();
//            }
            /**
             * @editor Trung.Nguyen
             * @date 11-Jan-2023
             * */
            List<TaxFee> details = objectMapper.readValue(etaxReceiptInfo.getTaxFees(), new TypeReference<List<TaxFee>>() {});
            long totalMoney = 0l;
            for (TaxFee detail : details) {
                if ((detail.getFeeAmount() == null) || detail.getFeeAmount().trim().isEmpty()) {
                    detail.setFeeAmount("0");
                }
                totalMoney += Long.parseLong(detail.getFeeAmount());
            }

            String dateToday = logicUtils.getDateSql(System.currentTimeMillis());
            String[] dateArr = dateToday.split("-");

            /**
             * @editor Trung.Nguyen
             * @date 17-Jan-2023
             * */
            String year = etaxPaymentInfo.getTransTime().substring(0,4);
            String month = etaxPaymentInfo.getTransTime().substring(4,6);
            String day = etaxPaymentInfo.getTransTime().substring(6,8);

            for (XWPFTable tbl : doc.getTables()) {
                for (XWPFTableRow row : tbl.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph p : cell.getParagraphs()) {
                            for (XWPFRun r : p.getRuns()) {
                                // Trung.Nguyen
                                replaceData(r, "${ref_no}", etaxPaymentInfo.getReferenceNumber());

                                replaceData(r, "${nnt_name}", etaxReceiptInfo.getTaxPayerName());
                                replaceData(r, "${nnt_mst}", etaxReceiptInfo.getTaxCode());

                                replaceData(r, "${nnt_diachi}", etaxReceiptInfo.getTaxPayerAddress());
                                // Trung.Nguyen
                                replaceData(r, "${nnt_quan}", ((etaxReceiptInfo.getTaxPayerDistrict() == null) || etaxReceiptInfo.getTaxPayerDistrict().trim().isEmpty())? "" : etaxReceiptInfo.getTaxPayerDistrict().trim().substring(etaxReceiptInfo.getTaxPayerDistrict().indexOf("-")+1));
                                replaceData(r, "${nnt_tp}", ((etaxReceiptInfo.getTaxPayerProvince() == null) || etaxReceiptInfo.getTaxPayerProvince().trim().isEmpty())? "" : etaxReceiptInfo.getTaxPayerProvince().trim().substring(etaxReceiptInfo.getTaxPayerProvince().indexOf("-")+1));

                                replaceData(r, "${nnt_tk}", etaxPaymentInfo.getDebitAccountNumber());
                                replaceData(r, "${kbnn_stk}", etaxReceiptInfo.getBenefitAccountNumber());

                                // fix code
                                replaceData(r, "${kbnn_tp}", etaxReceiptInfo.getProvince());
                                replaceData(r, "${nh_branch}", etaxReceiptInfo.getBenefitBankName());
                                replaceData(r, "${cq_qlt}", etaxReceiptInfo.getTaxInstitutionName());

//                                replaceData(r, "${day}", dateArr[2]);
                                replaceData(r, "${day}", day);
//                                replaceData(r, "${moh}", dateArr[1]);
                                replaceData(r, "${moh}", month);
//                                replaceData(r, "${year}", dateArr[0]);
                                replaceData(r, "${year}", year);

                                replaceData(r, "${total_money}", logicUtils.formatCurrency(totalMoney) + "đ");
                                replaceData(r, "${total_money_text}", logicUtils.convertMoneyToString(totalMoney + ""));
                                // Trung.Nguyen
                                replaceData(r, "${cq_thu}", etaxReceiptInfo.getTaxInstitutionCode());
                            }
                        }
                    }
                }
            }

            // insert row
            int i = 0;
            outerloop:
            for (XWPFTable tbl : doc.getTables()) {
                for (XWPFTableRow row : tbl.getRows()) {
                    i++;
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph p : cell.getParagraphs()) {
                            for (XWPFRun r : p.getRuns()) {
                                String text = r.getText(0);
                                if (text != null && text.contains("STT")) {
//                                    System.out.println();
//                                    for (int j = 0; j < details.size(); j++) {
//                                        ReceiptDetail receiptDetail = details.get(j);
//                                        XWPFTableRow copiedRow = new XWPFTableRow((CTRow) row.getCtRow().copy(), tbl);
//                                        clearRow(copiedRow);
//                                        copiedRow.getTableCells().get(0).setText(j + 1 + "");
//                                        copiedRow.getTableCells().get(1).setText(etaxReceiptInfo.getTaxNumber());
//                                        copiedRow.getTableCells().get(2).setText(etaxReceiptInfo.getTaxDate());
//                                        copiedRow.getTableCells().get(3).setText(receiptDetail.getNoiDung());
//                                        copiedRow.getTableCells().get(5).setText(logicUtils.formatCurrency(receiptDetail.getSoTien()) + "");
//                                        tbl.addRow(copiedRow, i);
//                                        i++;
//                                    }
                                    /**
                                     * @editor Trung.Nguyen
                                     * @date 11-Jan-2023
                                     * */
                                    for (int j = 0; j < details.size(); j++) {
                                        TaxFee taxFee = details.get(j);
                                        XWPFTableRow copiedRow = new XWPFTableRow((CTRow) row.getCtRow().copy(), tbl);
                                        clearRow(copiedRow);
                                        copiedRow.getTableCells().get(0).setText(j + 1 + "");
                                        copiedRow.getTableCells().get(1).setText(etaxReceiptInfo.getTaxNumber());
                                        copiedRow.getTableCells().get(2).setText(etaxReceiptInfo.getTaxDate());
                                        if("09".equals(etaxReceiptInfo.getTaxCategoryCode())) {
                                            copiedRow.getTableCells().get(3).setText(taxFee.getFeeCategoryName()+"+"+etaxReceiptInfo.getTaxReceiptCode());
                                        } else {
                                            copiedRow.getTableCells().get(3).setText(taxFee.getFeeCategoryName());
                                        }
                                        copiedRow.getTableCells().get(5).setText(logicUtils.formatCurrency(Long.parseLong(taxFee.getFeeAmount())) + "");
                                        if ((taxFee.getFeeCategoryCode() != null) && taxFee.getFeeCategoryCode().contains("+")) {
//                                            String C = "", TM = "", DBHC = "";
//                                            String[] feeCategoryCode = new String[0];
//                                            feeCategoryCode = taxFee.getFeeCategoryCode().split("\\+");
//                                            if (feeCategoryCode.length > 1) {
//                                                C = feeCategoryCode[1];
//                                            }
//                                            if (feeCategoryCode.length > 2) {
//                                                TM = feeCategoryCode[2];
//                                            }
//                                            if (feeCategoryCode.length > 3) {
//                                                DBHC = feeCategoryCode[3];
//                                            }
//                                            copiedRow.getTableCells().get(6).setText(C);
//                                            copiedRow.getTableCells().get(7).setText(TM);
//                                            copiedRow.getTableCells().get(8).setText(DBHC);
                                            FeeCategoryElement element = logicUtils.splitFeeCategoryCode(taxFee.getFeeCategoryCode());
                                            copiedRow.getTableCells().get(6).setText(element.getCategoryCode());
                                            copiedRow.getTableCells().get(7).setText(element.getSubCategoryCode());
                                            copiedRow.getTableCells().get(8).setText(element.getLocationCode());
                                        }
                                        tbl.addRow(copiedRow, i);
                                        i++;
                                    }
//                                    row.getTable().addRow(copiedRow);
                                    break outerloop;
                                }
                            }
                        }
                    }
                }
            }


//             save the docs
            try (FileOutputStream out2 = new FileOutputStream(outputFile)) {
                doc.write(out2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void replaceData(XWPFRun r, String field, String data) {
        String text = r.getText(0);
        if (text != null && text.contains(field)) {
            text = text.replace(field, data == null ? "" : data);
            r.setText(text, 0);
        }
    }

    private void clearRow(XWPFTableRow row) {
        for (XWPFTableCell cell : row.getTableCells()) {
            for (XWPFParagraph p : cell.getParagraphs()) {
                for (XWPFRun r : p.getRuns()) {
                    String text = r.getText(0);
                    if (text != null) {
                        r.setText("", 0);
                    }
                }
            }
        }
    }


}
