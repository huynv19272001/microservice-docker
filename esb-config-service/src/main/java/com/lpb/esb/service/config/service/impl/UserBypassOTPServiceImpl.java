package com.lpb.esb.service.config.service.impl;

import com.lpb.esb.service.common.model.response.ListModel;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.PageUtils;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.config.model.dto.EsbUser;
import com.lpb.esb.service.config.model.dto.OtpDTO;
import com.lpb.esb.service.config.model.entities.UserBypassOtpEntity;
import com.lpb.esb.service.config.repository.UserBypassOtpRepository;
import com.lpb.esb.service.config.service.UserBypassOTPService;
import com.lpb.esb.service.config.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class UserBypassOTPServiceImpl implements UserBypassOTPService {

    @Autowired
    UserBypassOtpRepository userBypassOTPRepository;

    //list all user
    @Override
    public ResponseModel getAllUser(int pageNumber, int pageSize) {
        ResponseModel responseModel = new ResponseModel();
        try {
            Page<UserBypassOtpEntity> list = userBypassOTPRepository.findAll((PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Order.desc("makerDt")))));
            ListModel<OtpDTO> listModel = PageUtils.initPageModel(list, pageNumber, pageSize, OtpDTO.class);
            if (list.getSize() <= 0) {
                LpbResCode lpbResCode = LpbResCode.builder()
                    .errorCode(EsbErrorCode.NO_DATA.label)
                    .errorDesc("Success")
                    .build();
                responseModel = ResponseModel.builder()
                    .resCode(lpbResCode)
                    .data(listModel)
                    .build();
            } else {
                LpbResCode lpbResCode = LpbResCode.builder()
                    .errorCode(EsbErrorCode.SUCCESS.label)
                    .errorDesc("Success")
                    .build();
                responseModel = ResponseModel.builder()
                    .resCode(lpbResCode)
                    .data(listModel)
                    .build();
            }
        } catch (Exception e) {
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Fail")
                .build();
            responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
        }
        return responseModel;
    }

    // detail user
    @Override
    public ResponseModel getDetailUser(String userId) {
        ResponseModel responseModel = new ResponseModel();
        try {
            UserBypassOtpEntity userBypassOtpEntity = userBypassOTPRepository.findByUserId(userId);
            OtpDTO otpDTO = OtpDTO.builder()
                .userId(userBypassOtpEntity.getUserId())
                .recordStat(userBypassOtpEntity.getRecordStat())
                .makerDt(userBypassOtpEntity.getMakerDt())
                .makerId(userBypassOtpEntity.getMakerId())
                .udf1(userBypassOtpEntity.getUdf1())
                .udf2(userBypassOtpEntity.getUdf2())
                .udf3(userBypassOtpEntity.getUdf3())
                .udf4(userBypassOtpEntity.getUdf4())
                .build();

            if (userBypassOtpEntity.equals("")) {
                LpbResCode lpbResCode = LpbResCode.builder()
                    .errorCode(EsbErrorCode.NO_DATA.label)
                    .errorDesc("Success")
                    .build();
                responseModel = ResponseModel.builder()
                    .resCode(lpbResCode)
                    .data(otpDTO)
                    .build();
            } else {
                LpbResCode lpbResCode = LpbResCode.builder()
                    .errorCode(EsbErrorCode.SUCCESS.label)
                    .errorDesc("Success")
                    .build();
                responseModel = ResponseModel.builder()
                    .resCode(lpbResCode)
                    .data(otpDTO)
                    .build();
            }
        } catch (Exception e) {
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Fail")
                .build();
            responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
        }
        return responseModel;
    }

    // insert
    @Override
    public ResponseModel save(OtpDTO otpDTO) {
        ResponseModel responseModel = new ResponseModel();
        UserBypassOtpEntity userBypassOTPEntity = new UserBypassOtpEntity();
        try {
            if (userBypassOTPRepository.findByUserId(otpDTO.getUserId()) ==  null) {
                userBypassOTPEntity.setUserId(otpDTO.getUserId());
                userBypassOTPEntity.setRecordStat(otpDTO.getRecordStat());
                userBypassOTPEntity.setMakerDt(new Date());
                userBypassOTPEntity.setMakerId(otpDTO.getMakerId());
                userBypassOTPEntity.setUdf1(DateUtils.formatDate(new Date()));
                userBypassOTPEntity.setUdf2(otpDTO.getUdf2());
                userBypassOTPRepository.save(userBypassOTPEntity);

                LpbResCode lpbResCode = LpbResCode.builder()
                    .errorCode(EsbErrorCode.SUCCESS.label)
                    .errorDesc("Insert Succesfully")
                    .build();
                responseModel = ResponseModel.builder()
                    .resCode(lpbResCode)
                    .build();
                return responseModel;
            } else {
                LpbResCode lpbResCode = LpbResCode.builder()
//                    .errorCode(EsbErrorCode.EXISTS.label)
                    .errorDesc("User already exists")
                    .build();
                responseModel = ResponseModel.builder()
                    .resCode(lpbResCode)
                    .build();
                return responseModel;
            }

        } catch (Exception e){
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Insert Failed")
                .build();
            responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
            return responseModel;
        }
    }

    // update
    @Override
    public void updateUser(OtpDTO otpDTOEntity, String checkerName) {
        Date date = new Date();
        userBypassOTPRepository.updateUser(otpDTOEntity.getRecordStat(), DateUtils.formatDate(date), checkerName, otpDTOEntity.getUserId());
    }
}
