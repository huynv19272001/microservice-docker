package com.lpb.service.sql.service.impl;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.service.sql.model.bean.ParamBean;
import com.lpb.service.sql.model.bean.ResultBean;
import com.lpb.service.sql.configuration.DBContextHolder;
import com.lpb.service.sql.utils.constants.Constants;
import com.lpb.service.sql.utils.constants.DBTypeEnum;
import com.lpb.service.sql.utils.constants.EnumInOutField;
import com.lpb.service.sql.utils.constants.EnumTypeField;
import com.lpb.service.sql.model.*;
import com.lpb.service.sql.utils.rsa.PasswordDigest;
import com.lpb.service.sql.service.ICommonService;
import com.lpb.service.sql.service.ITCLService;
import com.lpb.service.sql.utils.Utilities;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Configurable
@Log4j2
public class TCLServiceImpl extends SQLServiceImpl implements ITCLService {
    @Autowired
    ICommonService iCommonService;

    public ResponseModel startExecute(RequestDTO requestDTO) {
        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Successful!").build();
        ResponseModel responseModel = ResponseModel.builder().resCode(resCode).build();
        DBContextHolder.setCurrentDb(DBTypeEnum.ESB);

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setSERVICE_ID(requestDTO.getHeader().getServiceId());
        serviceDTO.setPRODUCT_CODE(requestDTO.getHeader().getProductCode());

        responseModel = iCommonService.getQuerySQL(Constants.ROLE_TCL, serviceDTO);
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
        writeValueAsString(responseModel);

        if (responseModel.getResCode().getErrorCode().equals(EsbErrorCode.SUCCESS.label)) {
            List<ParamDTO> responseParams = (List<ParamDTO>) responseModel.getData();
            responseModel = ResponseModel.builder().resCode(responseModel.getResCode()).data(convertResult(requestDTO.getBody().getParams(), responseParams)).build();
        }
        return responseModel;
    }

    public ResponseModel activeSQLDynamic(QueryVO queryVo, List<ParamDTO> listParam) {
        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Successful!").build();
        ResponseModel responseModel = ResponseModel.builder().resCode(resCode).build();

        if (queryVo.getUDF3() != null && queryVo.getUDF3().toUpperCase().equals("Y")) {
            PassCore passCore = new PassCore();
            for (ParamDTO paramDTO : listParam) {
                if (paramDTO.getName().equals("PASSWORD_CORE")) {
                    passCore.setPASSWORD(paramDTO.getValue());
                    PasswordDigest.generatePass(passCore);
                }
            }
            ParamDTO paramDTO = new ParamDTO();
            paramDTO.setName("SALT");
            paramDTO.setValue(passCore.getSALT());
            listParam.add(paramDTO);

            paramDTO = new ParamDTO();
            paramDTO.setName("GENERATE_PASSWORD");
            paramDTO.setValue(passCore.getGENERATE_PASSWORD());
            listParam.add(paramDTO);
        }

        List<ParamDTO> listResField = new ArrayList<ParamDTO>();
        String funcName = queryVo.getSqlInfo().getSqlCommand();
        List<SQLParamDTO> listVariableDB = queryVo.getSqlInfo().getListParamSQL();
        List<ParamBean> listBean = new ArrayList<ParamBean>();

        createListBean(listBean, listVariableDB, listParam);

        log.info("Execute PKG: " + queryVo.getSqlInfo().getSqlCommand());
        if (queryVo.getUDF6() != null && queryVo.getUDF6().equals(Constants.CONN_NAME_MYSQL)) {
            responseModel = iCommonService.execPackageMySQL(funcName, listBean);
        } else {
            responseModel = iCommonService.execPackage(funcName, listBean);
        }
        log.debug("End Execute PKG: " + responseModel.getResCode().getErrorCode() + " " + responseModel.getErrorDesc());

        List<ResultBean> listResultBean = new ArrayList<>();
        if (responseModel.getResCode().getErrorCode().equals(EsbErrorCode.SUCCESS.label)) {
            listResultBean = (List<ResultBean>) responseModel.getData();
        } else {
            return responseModel;
        }

        log.debug("List ResultBean: " + listResultBean.size());
        responseModel = transformData(queryVo, listVariableDB, listResultBean);
        return responseModel;
    }

    public void createListBean(List<ParamBean> listBean, List<SQLParamDTO> listVariableDB, List<ParamDTO> listParam) {

        List<SQLParamDTO> listParamFill = new ArrayList<SQLParamDTO>(listVariableDB);
        for (ParamDTO paramInput : listParam) {
            // transfer from Input
            for (SQLParamDTO varDB : listVariableDB) {
                if (paramInput.getName().trim().toUpperCase().equals(varDB.getColName().trim())) {
                    listBean.add(createParamBean(varDB, paramInput));
                    listParamFill.remove(varDB);
                }
            }
        }
        // create param from DB
        for (SQLParamDTO varDB : listParamFill) {
            // create param from DB
            if (varDB.getColName().trim().equals("ERRORCODE")) {
                if (varDB.getInOut().trim().equals(EnumInOutField.INOUT.toString())) {
                    listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.VARCHAR, "", EnumInOutField.INOUT));
                } else if (varDB.getInOut().trim().equals(EnumInOutField.OUTPUT.toString())) {
                    listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.VARCHAR, "", EnumInOutField.OUTPUT));
                }
            } else if (varDB.getColName().trim().equals("ERRORDESC")) {
                if (varDB.getInOut().trim().equals(EnumInOutField.INOUT.toString())) {
                    listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.VARCHAR, "", EnumInOutField.INOUT));
                } else if (varDB.getInOut().trim().equals(EnumInOutField.OUTPUT.toString())) {
                    listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.VARCHAR, "", EnumInOutField.OUTPUT));
                }
            }
//            else if (varDB.getColName().trim().equals("USERID")) {
//                listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.VARCHAR, fcubsHeader.getUSERID(), EnumInOutField.INPUT));
//            } else if (varDB.getColName().trim().equals("MSGID")) {
//
//                listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.VARCHAR, fcubsHeader.getMSGID(), EnumInOutField.INPUT));
//            } else if (varDB.getColName().trim().equals("SOURCE")) {
//                listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.VARCHAR, fcubsHeader.getSOURCE(), EnumInOutField.INPUT));
//            }
            else {
                if (varDB.getInOut().trim().equals(EnumInOutField.INOUT.toString())) {
                    if (varDB.getValType().trim().equals(EnumTypeField.VARCHAR.toString())) {
                        listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.VARCHAR, "", EnumInOutField.INOUT));
                    } else if (varDB.getValType().trim().equals(EnumTypeField.INT.toString())) {
                        listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.INT, null, EnumInOutField.INOUT));
                    } else if (varDB.getValType().trim().equals(EnumTypeField.DOUBLE.toString())) {
                        listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.DOUBLE, null, EnumInOutField.INOUT));
                    } else if (varDB.getValType().trim().equals(EnumTypeField.CLOB.toString())) {
                        listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.CLOB, null, EnumInOutField.INOUT));
                    } else {
                        listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.DATE, null, EnumInOutField.INOUT));
                    }
                } else if (varDB.getInOut().trim().equals(EnumInOutField.OUTPUT.toString())) {
                    if (varDB.getValType().trim().equals(EnumTypeField.VARCHAR.toString())) {
                        listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.VARCHAR, "", EnumInOutField.OUTPUT));
                    } else if (varDB.getValType().trim().equals(EnumTypeField.INT.toString())) {
                        listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.INT, null, EnumInOutField.OUTPUT));
                    } else if (varDB.getValType().trim().equals(EnumTypeField.DOUBLE.toString())) {
                        listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.DOUBLE, null, EnumInOutField.OUTPUT));
                    } else if (varDB.getValType().trim().equals(EnumTypeField.CLOB.toString())) {
                        listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.CLOB, null, EnumInOutField.OUTPUT));
                    } else if (varDB.getValType().trim().equals(EnumTypeField.RESULT_SET.toString())) {
                        listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.RESULT_SET, null, EnumInOutField.OUTPUT));
                    } else if (varDB.getValType().trim().equals(EnumTypeField.CURSOR.toString())) {
                        listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.CURSOR, null, EnumInOutField.OUTPUT));
                    } else {
                        listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.DATE, null, EnumInOutField.OUTPUT));
                    }
                } else {
                    if (varDB.getValType().trim().equals(EnumTypeField.VARCHAR.toString())) {
                        listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.VARCHAR, "", EnumInOutField.INPUT));
                    } else if (varDB.getValType().trim().equals(EnumTypeField.INT.toString())) {
                        listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.INT, null, EnumInOutField.INPUT));
                    } else if (varDB.getValType().trim().equals(EnumTypeField.DOUBLE.toString())) {
                        listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.DOUBLE, null, EnumInOutField.INPUT));
                    } else if (varDB.getValType().trim().equals(EnumTypeField.CLOB.toString())) {
                        listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.CLOB, null, EnumInOutField.INPUT));
                    } else if (varDB.getValType().trim().equals(EnumTypeField.CURSOR.toString())) {
                        listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.CURSOR, null, EnumInOutField.INPUT));
                    } else {
                        listBean.add(new ParamBean(varDB.getIdx(), EnumTypeField.DATE, null, EnumInOutField.INPUT));
                    }
                }
            }
        }
    }

    public ParamBean createParamBean(SQLParamDTO varDB, ParamDTO paramInput) {
        if (varDB.getInOut().trim().equals(EnumInOutField.INPUT.toString())) {
            if (varDB.getValType().trim().equals(EnumTypeField.VARCHAR.toString())) {
                return new ParamBean(varDB.getIdx(), EnumTypeField.VARCHAR, paramInput.getValue(), EnumInOutField.INPUT);
            } else if (varDB.getValType().trim().equals(EnumTypeField.DATE.toString())) {
                return new ParamBean(varDB.getIdx(), EnumTypeField.DATE, paramInput.getValue(), EnumInOutField.INPUT);
            } else if (varDB.getValType().trim().equals(EnumTypeField.DOUBLE.toString())) {
                return new ParamBean(varDB.getIdx(), EnumTypeField.DOUBLE, paramInput.getValue(), EnumInOutField.INPUT);
            } else if (varDB.getValType().trim().equals(EnumTypeField.CLOB.toString())) {
                return new ParamBean(varDB.getIdx(), EnumTypeField.CLOB, paramInput.getValue(), EnumInOutField.INPUT);
            } else if (varDB.getValType().trim().equals(EnumTypeField.XMLTYPE.toString())) {
                return new ParamBean(varDB.getIdx(), EnumTypeField.XMLTYPE, paramInput.getValue(), EnumInOutField.INPUT);
            } else {
                return new ParamBean(varDB.getIdx(), EnumTypeField.VARCHAR, paramInput.getValue(), EnumInOutField.INPUT);
            }
        } else if (varDB.getInOut().trim().equals(EnumInOutField.OUTPUT.toString())) {
            if (varDB.getValType().equals(EnumTypeField.VARCHAR.toString())) {
                return new ParamBean(varDB.getIdx(), EnumTypeField.VARCHAR, paramInput.getValue(), EnumInOutField.OUTPUT);
            } else if (varDB.getValType().trim().equals(EnumTypeField.DATE.toString())) {
                return new ParamBean(varDB.getIdx(), EnumTypeField.DATE, paramInput.getValue(), EnumInOutField.OUTPUT);
            } else if (varDB.getValType().trim().equals(EnumTypeField.DOUBLE.toString())) {
                return new ParamBean(varDB.getIdx(), EnumTypeField.DOUBLE, paramInput.getValue(), EnumInOutField.OUTPUT);
            } else if (varDB.getValType().trim().equals(EnumTypeField.CLOB.toString())) {
                return new ParamBean(varDB.getIdx(), EnumTypeField.CLOB, paramInput.getValue(), EnumInOutField.OUTPUT);
            } else if (varDB.getValType().trim().equals(EnumTypeField.CURSOR.toString())) {
                return new ParamBean(varDB.getIdx(), EnumTypeField.CURSOR, paramInput.getValue(), EnumInOutField.OUTPUT);
            } else {
                return new ParamBean(varDB.getIdx(), EnumTypeField.VARCHAR, paramInput.getValue(), EnumInOutField.OUTPUT);
            }
        } else {
            if (varDB.getValType().trim().equals(EnumTypeField.VARCHAR.toString())) {
                return new ParamBean(varDB.getIdx(), EnumTypeField.VARCHAR, paramInput.getValue(), EnumInOutField.INOUT);
            } else if (varDB.getValType().trim().equals(EnumTypeField.DATE.toString())) {
                return new ParamBean(varDB.getIdx(), EnumTypeField.DATE, paramInput.getValue(), EnumInOutField.INOUT);
            } else if (varDB.getValType().trim().equals(EnumTypeField.DOUBLE.toString())) {
                return new ParamBean(varDB.getIdx(), EnumTypeField.DOUBLE, paramInput.getValue(), EnumInOutField.INOUT);
            } else if (varDB.getValType().trim().equals(EnumTypeField.CLOB.toString())) {
                return new ParamBean(varDB.getIdx(), EnumTypeField.CLOB, paramInput.getValue(), EnumInOutField.INOUT);
            } else if (varDB.getValType().trim().equals(EnumTypeField.CURSOR.toString())) {
                return new ParamBean(varDB.getIdx(), EnumTypeField.CURSOR, paramInput.getValue(), EnumInOutField.INOUT);
            } else {
                return new ParamBean(varDB.getIdx(), EnumTypeField.DOUBLE, paramInput.getValue(), EnumInOutField.INOUT);
            }
        }
    }

    public ResponseModel transformData(QueryVO queryVo, List<SQLParamDTO> listVariableDB, List<ResultBean> resultBeans) {
        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Successful!").build();
        ResponseModel responseModel = ResponseModel.builder().resCode(resCode).build();
        List<ParamDTO> listResField = new ArrayList<ParamDTO>();

        for (SQLParamDTO paramDB : listVariableDB) {
            for (ResultBean resultBean : resultBeans) {
                log.debug("result: " + resultBean.getIdx() + " val: " + resultBean.getValObj());
                if (paramDB.getIdx() == resultBean.getIdx()) {
                    ParamDTO paraRes = new ParamDTO();
                    if (paramDB.getColName().equals("RETURN")) {
                        if (paramDB.getSqlClause().trim().equals(resultBean.getValObj().toString())) {
                            resCode.setErrorCode(EsbErrorCode.SUCCESS.label);
                        } else {
                            resCode.setErrorCode(EsbErrorCode.TRAN_FAIL.label);
                        }
                    } else if (paramDB.getColName().trim().equals("ERRORCODE")) {
                        resCode.setRefCode(resultBean.getValObj() == null ? "" : resultBean.getValObj().toString());
                    } else if (paramDB.getColName().trim().equals("ERRORDESC")) {
                        resCode.setRefDesc(resultBean.getValObj() == null ? "" : resultBean.getValObj().toString());
                    } else {
                        if (paramDB.getUdf1() != null && paramDB.getUdf1().trim().length() >= 1) {
                            boolean flag = false;
                            if (paramDB.getUdf1().equals("C")) {
                                flag = resultBean.getValObj().toString().trim().toUpperCase().contains(paramDB.getSqlClause().trim().toUpperCase());
                            } else {
                                flag = paramDB.getSqlClause().trim().toUpperCase().equals(resultBean.getValObj().toString().trim().toUpperCase());
                            }
                            if (flag) {
                                resCode.setErrorCode(EsbErrorCode.SUCCESS.label);
                            } else {
                                resCode.setErrorCode(EsbErrorCode.TRAN_FAIL.label);
                            }
                            resCode.setRefCode(resultBean.getValObj().toString());
                            // esb.loadErrorDesc(fcubsError);
                        }
                        paraRes.setName(paramDB.getColName());
                        paraRes.setValue(Utilities.convertObjSelectToStr(resultBean.getValObj(), paramDB.getValType()));
                        listResField.add(paraRes);
                    }
                }
            }
        }
        if (queryVo.getCONNECTOR_NAME().equals(Constants.CONN_NAME_PMDG)) {
            if (resCode.getRefCode() != null && !resCode.getRefCode().equals("000")) {
                resCode.setErrorCode(EsbErrorCode.TRAN_FAIL.label);
            }
        }
        DBContextHolder.setCurrentDb(DBTypeEnum.ESB);
        resCode = iCommonService.loadErrorDesc(resCode);
        responseModel = ResponseModel.builder().resCode(resCode).data(listResField).build();
        return responseModel;
    }

    public List<ParamDTO> convertResult(List<ParamDTO> listParamRequest, List<ParamDTO> listParamResponse) {
        Boolean check = false;
        for (ParamDTO paramResDTO : listParamResponse) {
            check = false;
            for (ParamDTO paramDTO : listParamRequest) {
                if (paramDTO.getName().equals(paramResDTO.getName())) {
                    paramDTO.setValue(paramResDTO.getValue());
                    check = true;
                }
            }
            if (!check) listParamRequest.add(paramResDTO);
        }
        writeValueAsString(listParamRequest);
        return listParamRequest;
    }
}
