package com.lpb.esb.etax.payment.util;

import com.lpb.esb.etax.payment.model.data.FeeCategoryElement;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by tudv1 on 2022-06-22
 */
@Component
public class LogicUtils {
    public String convertMoneyToString(String number) {
        String[] strTachPhanSauDauPhay;
        if (number.contains(".") || number.contains(",")) {
            strTachPhanSauDauPhay = number.split(".");
            String chuoi = convertMoneyToString(strTachPhanSauDauPhay[0]) + "đồng";
            chuoi = chuoi.substring(0, 1).toUpperCase() + chuoi.substring(1);
            return chuoi;
        }

        String[] dv = {"", "mươi", "trăm", "nghìn", "triệu", "tỉ,"};
        String[] cs = {"không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín"};
        String doc;
        int i, j, k, n, len, found, ddv, rd;

        len = number.length();
        number += "ss";
        doc = "";
        found = 0;
        ddv = 0;
        rd = 0;

        i = 0;
        while (i < len) {
            //So chu so o hang dang duyet
            n = (len - i + 2) % 3 + 1;

            //Kiem tra so 0
            found = 0;
            for (j = 0; j < n; j++) {
                if (number.charAt(i + j) != '0') {
                    found = 1;
                    break;
                }
            }

            //Duyet n chu so
            if (found == 1) {
                rd = 1;
                for (j = 0; j < n; j++) {
                    ddv = 1;
                    switch (number.charAt(i + j)) {
                        case '0':
                            if (n - j == 3) doc += cs[0] + " ";
                            if (n - j == 2) {
                                if (number.charAt(i + j + 1) != '0') doc += "linh ";
                                ddv = 0;
                            }
                            break;
                        case '1':
                            if (n - j == 3) doc += cs[1] + " ";
                            if (n - j == 2) {
                                doc += "mười ";
                                ddv = 0;
                            }
                            if (n - j == 1) {
                                if (i + j == 0) k = 0;
                                else k = i + j - 1;

                                if (number.charAt(k) != '1' && number.charAt(k) != '0')
                                    doc += "mốt ";
                                else
                                    doc += cs[1] + " ";
                            }
                            break;
                        case '5':
                            if ((i + j == len - 1) || (i + j + 3 == len - 1))
                                doc += "lăm ";
                            else
                                doc += cs[5] + " ";
                            break;
                        default:
                            doc += cs[(int) number.charAt(i + j) - 48] + " ";
                            break;
                    }

                    //Doc don vi nho
                    if (ddv == 1) {
                        doc += ((n - j) != 1) ? dv[n - j - 1] + " " : dv[n - j - 1];
                    }
                }
            }


            //Doc don vi lon
            if (len - i - n > 0) {
                if ((len - i - n) % 9 == 0) {
                    if (rd == 1)
                        for (k = 0; k < (len - i - n) / 9; k++)
                            doc += "tỉ ";
                    rd = 0;
                } else if (found != 0) doc += dv[((len - i - n + 1) % 9) / 3 + 2] + " ";
            }

            i += n;
        }

        if (len == 1)
            if (number.charAt(0) == '0' || number.charAt(0) == '5') return cs[(int) number.charAt(0) - 48];

        doc = doc.substring(0, 1).toUpperCase() + doc.substring(1);
        return doc + "đồng";
    }

    public String getDateSql(long dt) {
        SimpleDateFormat dfDay = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(dt);
        return dfDay.format(date);
    }

    public Resource load(String rootDir, String filename) {
        Path root = Paths.get(rootDir);
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    // Trung.Nguyen
    public static String formatCurrency(long value) {
        DecimalFormat df2 = new DecimalFormat("###,###,###,###,###");
        String valueFormat = df2.format(value);
        return valueFormat;
    }

    /**
     * @author: Trung.Nguyen
     * @since: 12-Jan-2023
     * */
    public FeeCategoryElement splitFeeCategoryCode(String feeCategoryCode) {
        FeeCategoryElement element = new FeeCategoryElement();
        String[] arr = new String[0];
        arr = feeCategoryCode.split("\\+");
        if (arr.length > 1) {
            element.setCategoryCode(arr[1]);
        }
        if (arr.length > 2) {
            element.setSubCategoryCode(arr[2]);
        }
        if (arr.length > 3) {
            element.setLocationCode(arr[3]);
        }
        return element;
    }

}
