package com.lpb.service.sql.service.impl;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.service.sql.model.bean.ParamBean;
import com.lpb.service.sql.service.command.impl.ESBCommandService;
import com.lpb.service.sql.service.command.impl.ESBQueryService;
import com.lpb.service.sql.model.ServiceDTO;
import com.lpb.service.sql.service.ICommonService;
import lombok.extern.log4j.Log4j2;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class CommonServiceImpl implements ICommonService {
    @Autowired
    ESBQueryService queryService;

    @Autowired
    ESBCommandService commandService;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager getEntityManager() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager;
    }

    @Transactional
    public ResponseModel getQuerySQL(String hasRole, ServiceDTO serviceDTO) {
        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Successful!").build();
        final ResponseModel[] responseModel = {ResponseModel.builder().resCode(resCode).build()};

        try {
            org.hibernate.Session session = getEntityManager().unwrap(org.hibernate.Session.class);
            session.doWork(new Work() {
                @Override
                public void execute(Connection conn) {
                    try {
                        responseModel[0] = queryService.loadSqlInfoService(conn, hasRole, serviceDTO);
                        conn.close();
                    } catch (SQLException ex) {
                        log.error("getQuerySQL: " + ex.getMessage());
                    }
                }
            });
        } catch (Exception ex) {
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc(ex.getMessage()).build();
            responseModel[0] = ResponseModel.builder().resCode(resCode).build();
        }

        return responseModel[0];
    }

    @Transactional
    public ResponseModel selectCMD(String cmdSQL, List<ParamBean> listField) {
        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Successful!").build();
        final ResponseModel[] responseModel = {ResponseModel.builder().resCode(resCode).build()};

        try {
            org.hibernate.Session session = getEntityManager().unwrap(org.hibernate.Session.class);
            final Map<String, List<Object>>[] result = new Map[]{null};
            session.doWork(new Work() {
                @Override
                public void execute(Connection conn) {
                    try {
                        responseModel[0] = commandService.selectCMD(conn, cmdSQL, listField);
                        conn.close();
                    } catch (SQLException ex) {
                        log.error("selectCMD: " + ex.getMessage());
                    }
                }
            });
        } catch (Exception ex) {
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc(ex.getMessage()).build();
            responseModel[0] = ResponseModel.builder().resCode(resCode).build();
        }
        return responseModel[0];
    }

    @Override
    public ResponseModel execPackage(String funcName, List<ParamBean> listField) {
        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Successful!").build();
        final ResponseModel[] responseModel = {ResponseModel.builder().resCode(resCode).build()};

        try {
            org.hibernate.Session session = getEntityManager().unwrap(org.hibernate.Session.class);
            final Map<String, List<Object>>[] result = new Map[]{null};
            session.doWork(new Work() {
                @Override
                public void execute(Connection conn) {
                    try {
                        responseModel[0] = commandService.execPackage(conn, funcName, listField);
                        conn.close();
                    } catch (SQLException ex) {
                        log.error("execPackage: " + ex.getMessage());
                    }
                }
            });
        } catch (Exception ex) {
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc(ex.getMessage()).build();
            responseModel[0] = ResponseModel.builder().resCode(resCode).build();
        }
        return responseModel[0];
    }

    @Override
    public ResponseModel execPackageMySQL(String funcName, List<ParamBean> listField) {
        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Successful!").build();
        final ResponseModel[] responseModel = {ResponseModel.builder().resCode(resCode).build()};

        try {
            org.hibernate.Session session = getEntityManager().unwrap(org.hibernate.Session.class);
            final Map<String, List<Object>>[] result = new Map[]{null};
            session.doWork(new Work() {
                @Override
                public void execute(Connection conn) {
                    try {
                        responseModel[0] = commandService.execPackageMySQL(conn, funcName, listField);
                        conn.close();
                    } catch (SQLException ex) {
                        log.error("execPackageMySQL: " + ex.getMessage());
                    }
                }
            });
        } catch (Exception ex) {
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc(ex.getMessage()).build();
            responseModel[0] = ResponseModel.builder().resCode(resCode).build();
        }
        return responseModel[0];
    }

    @Override
    public LpbResCode loadErrorDesc(LpbResCode lpbResCode) {
        final LpbResCode[] resCode = {LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Successful!").build()};
        try {
            org.hibernate.Session session = getEntityManager().unwrap(org.hibernate.Session.class);
            final Map<String, List<Object>>[] result = new Map[]{null};
            session.doWork(new Work() {
                @Override
                public void execute(Connection conn) {
                    try {
                        resCode[0] = queryService.loadErrorDesc(conn, lpbResCode);
                        conn.close();
                    } catch (SQLException ex) {
                        log.error("loadErrorDesc: " + ex.getMessage());
                    }
                }
            });
        } catch (Exception ex) {
            resCode[0] = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc(ex.getMessage()).build();
        }
        return resCode[0];
    }
}
