package com.lpb.service.sql.service.impl;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.service.sql.model.bean.ParamBean;
import com.lpb.service.sql.configuration.DBContextHolder;
import com.lpb.service.sql.utils.constants.Constants;
import com.lpb.service.sql.utils.constants.DBTypeEnum;
import com.lpb.service.sql.utils.constants.EnumTypeField;
import com.lpb.service.sql.model.*;
import com.lpb.service.sql.service.ICommonService;
import com.lpb.service.sql.service.IDMLService;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.lpb.service.sql.utils.Utilities.convertObjSelectToStr;

@Service
@Configurable
@Log4j2
public class DMLServiceImpl extends SQLServiceImpl implements IDMLService {
    @Autowired
    ICommonService iCommonService;

    public ResponseModel startExecute(RequestDTO requestDTO) {
        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Successful!").build();
        ResponseModel responseModel = ResponseModel.builder().resCode(resCode).build();

        DBContextHolder.setCurrentDb(DBTypeEnum.ESB);

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setSERVICE_ID(requestDTO.getHeader().getServiceId());
        serviceDTO.setPRODUCT_CODE(requestDTO.getHeader().getProductCode());

        responseModel = iCommonService.getQuerySQL(Constants.ROLE_DML, serviceDTO);
        log.info("getQuerySQL: " + responseModel.getResCode().getErrorCode() + " " + responseModel.getResCode().getErrorDesc());


        if (!responseModel.getResCode().getErrorCode().equals(EsbErrorCode.SUCCESS.label)) {
            return responseModel;
        }

        QueryVO queryVO = new QueryVO();
        queryVO = (QueryVO) responseModel.getData();
        writeValueAsString(queryVO);
        log.info("Execute: " + requestDTO.getHeader().getMsgId());

        switchDataSource(queryVO);
        responseModel = activeSQLDynamic(queryVO, requestDTO.getBody().getParams());

        log.info("End Execute: " + requestDTO.getHeader().getMsgId());

        if (responseModel.getResCode().getErrorCode().equals(EsbErrorCode.SUCCESS.label)) {
            Map<String, List<Object>> mapRsDynamic = (Map<String, List<Object>>) responseModel.getData();
            responseModel = ResponseModel.builder().resCode(responseModel.getResCode()).data(convertResult(mapRsDynamic)).build();
        }
        writeValueAsString(responseModel);
        return responseModel;
    }

    public ResponseModel activeSQLDynamic(QueryVO queryVo, List<ParamDTO> listParamInput) {
        StringBuilder buSql = new StringBuilder();
        buSql.append(queryVo.getSqlInfo().getSqlCommand());
        buSql.append(" ");

        SQLInfoDTO sqlInfo = queryVo.getSqlInfo();
        List<SQLParamDTO> listParamDB = sqlInfo.getListParamSQL();

        List<ParamBean> listBean = new ArrayList<ParamBean>();
        int i = 1;
        for (ParamDTO paramDTO : listParamInput) {
            for (SQLParamDTO paramDB : listParamDB) {
                if ((paramDB.getColName().trim().toUpperCase().equals(paramDTO.getName().trim().toUpperCase())) && (paramDTO.getValue().trim().length() > 0)) {
                    buSql.append(paramDB.getSqlClause());
                    buSql.append(" ");
                    if (paramDB.getValType().trim().equals(EnumTypeField.VARCHAR.toString())) {
                        ParamBean paramBean = ParamBean.builder().idx(i).typeField(EnumTypeField.VARCHAR).valField(paramDTO.getValue()).build();
                        listBean.add(paramBean);
                    } else if (paramDB.getValType().trim().equals(EnumTypeField.INT.toString())) {
                        ParamBean paramBean = ParamBean.builder().idx(i).typeField(EnumTypeField.INT).valField(paramDTO.getValue()).build();
                        listBean.add(paramBean);
                    } else if (paramDB.getValType().trim().equals(EnumTypeField.DOUBLE.toString())) {
                        ParamBean paramBean = ParamBean.builder().idx(i).typeField(EnumTypeField.DOUBLE).valField(paramDTO.getValue()).build();
                        listBean.add(paramBean);
                    } else if (paramDB.getValType().trim().equals(EnumTypeField.DATE.toString())) {
                        String dateInput = paramDTO.getValue().trim();
                        if (dateInput.contains("-")) {
                            String[] splFromToDt = dateInput.split("-");
                            ParamBean paramBean = ParamBean.builder().idx(i).typeField(EnumTypeField.DATE).valField(splFromToDt[0]).build();
                            listBean.add(paramBean);
                            i++;
                            paramBean = ParamBean.builder().idx(i).typeField(EnumTypeField.DATE).valField(splFromToDt[1]).build();
                            listBean.add(paramBean);
                        } else {
                            ParamBean paramBean = ParamBean.builder().idx(i).typeField(EnumTypeField.DATE).valField(dateInput).build();
                            listBean.add(paramBean);
                            i++;
                            paramBean = ParamBean.builder().idx(i).typeField(EnumTypeField.DATE).valField(dateInput).build();
                            listBean.add(paramBean);
                        }
                    } else if (paramDB.getValType().trim().equals(EnumTypeField.DATETIME.toString())) {
                        String dateInput = paramDTO.getValue().trim();
                        if (dateInput.contains("-")) {
                            String[] splFromToDt = dateInput.split("-");
                            ParamBean paramBean = ParamBean.builder().idx(i).typeField(EnumTypeField.DATETIME).valField(splFromToDt[0] + " 00:00:00").build();
                            listBean.add(paramBean);
                            i++;
                            paramBean = ParamBean.builder().idx(i).typeField(EnumTypeField.DATETIME).valField(splFromToDt[1] + " 23:59:59").build();
                            listBean.add(paramBean);
                        } else {
                            ParamBean paramBean = ParamBean.builder().idx(i).typeField(EnumTypeField.DATETIME).valField(dateInput + " 00:00:00").build();
                            listBean.add(paramBean);
                            i++;
                            paramBean = ParamBean.builder().idx(i).typeField(EnumTypeField.DATETIME).valField(dateInput + " 23:59:59").build();
                            listBean.add(paramBean);
                        }
                    } else {
                        ParamBean paramBean = ParamBean.builder().idx(i).typeField(EnumTypeField.VARCHAR).valField(paramDTO.getValue()).build();
                        listBean.add(paramBean);
                    }
                }
            }
            i++;
        }

        // Add more sql
        for (SQLParamDTO paramDB : listParamDB) {
            if (paramDB.getUdf1() != null && paramDB.getUdf1().trim().length() >= 1) {
                buSql.append(paramDB.getUdf1());
            }
        }

        log.info("Execute SQL: " + buSql);
        ResponseModel responseModel = iCommonService.selectCMD(buSql.toString(), listBean);
        log.info("Execute SQL END: " + responseModel.getErrorCode() + " " + responseModel.getErrorDesc());
        return responseModel;
    }

    public List<List<ColumnDTO>> convertResult(Map<String, List<Object>> data) {
        List<String> listColumn = new ArrayList<>(data.keySet());
        List<Object> listRow = data.get(listColumn.get(0));

        List<List<ColumnDTO>> rows = new ArrayList<>();
        for (int i = 0; i < listRow.size(); i++) {
            List<ColumnDTO> columns = new ArrayList<>();
            for (String col : listColumn) {
                columns.add(ColumnDTO.builder().name(col).value(convertObjSelectToStr(data.get(col).get(i), "")).build());
            }
            rows.add(columns);
        }
        return rows;
    }

    public List<List<JSONObject>> convertResult2(Map<String, List<Object>> data) {
        List<String> listColumn = new ArrayList<>(data.keySet());
        List<Object> listRow = data.get(listColumn.get(0));

        List<List<JSONObject>> rows = new ArrayList<>();
        for (int i = 0; i < listRow.size(); i++) {
            List<JSONObject> columns = new ArrayList<>();
            for (String col : listColumn) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(col, convertObjSelectToStr(data.get(col).get(i), ""));
                columns.add(jsonObject);
            }
            rows.add(columns);
        }
        return rows;
    }
}
