package com.esb.card.rest;

import com.esb.card.dto.availinfocard.AvailInfoCardREQDTO;
import com.esb.card.dto.canceldpp.CancelDppREQDTO;
import com.esb.card.dto.cardtranlimit.GetCardTranLimitREQDTO;
import com.esb.card.dto.checklatedatcard.CheckLateDateCardByCifREQDTO;
import com.esb.card.dto.creditcardinfo.CreditCardInfoREQDTO;
import com.esb.card.dto.debitcardinfo.DebitCardInfoREQDTO;
import com.esb.card.dto.getfeedpp.GetFeeDPPREQDTO;
import com.esb.card.dto.getinfodpp.GetInfoDPPREQDTO;
import com.esb.card.dto.getlistdpp.GetListDPPREQDTO;
import com.esb.card.dto.getlisttrans.GetListTransREQDTO;
import com.esb.card.dto.listcardinfo.CardInfoREQDTO;
import com.esb.card.dto.updatelimitpayment.UpdateLimitPaymentREQDTO;
import com.esb.card.dto.updatelinkacc.UpdateLinkAccREQDTO;
import com.esb.card.service.*;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.CommonException;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/card")
@Log4j2
public class CardController {
    @Autowired
    private ICardService cardService;

    @Autowired
    private GetInfoDPPService getInfoDPPService;

    @Autowired
    private GetFeeDPPService getFeeDPPService;

    @Autowired
    private UpdateLinkAccService updateLinkAccService;

    @Autowired
    private GetListTransService getListTransService;

    @Autowired
    private ListDPPService listDPPService;

    @Autowired
    private CreditCardInfoService creditCardInfoService;

    @Autowired
    private CancelDppService cancelDppService;

    @Autowired
    private CheckLateDateCardByCifService checkLateDateCardByCifService;

    @Autowired
    private GetCardTranLimitService getCardTranLimitService;

    @Operation(description = "Lấy thông tin thẻ")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "01", description = "Không có dữ liệu", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping(value = "list-card-info")
    public ResponseEntity<?> listCardInfo(@RequestBody CardInfoREQDTO infoRequest) {
        try {
            ResponseModel responseModel = cardService.getCardInfo(infoRequest);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Lấy thông tin thẻ debit card")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "01", description = "Không có dữ liệu", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping(value = "debit-card-info")
    public ResponseEntity<?> debitCardInfo(@RequestBody DebitCardInfoREQDTO infoRequest) {
        try {
            ResponseModel responseModel = cardService.getDebitCardInfo(infoRequest);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Lấy danh sách tài khoản gắn với thẻ")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "01", description = "Không có dữ liệu", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping(value = "list-acc")
    public ResponseEntity<?> listAcc(@RequestBody DebitCardInfoREQDTO infoRequest) {
        try {
            ResponseModel responseModel = cardService.getListAcc(infoRequest);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Lấy số dư khả dụng gắn với thẻ")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "01", description = "Không có dữ liệu", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping(value = "avail-info-card")
    public ResponseEntity<?> availInfoCard(@RequestBody AvailInfoCardREQDTO infoRequest) {
        try {
            ResponseModel responseModel = cardService.getAvailInfoCard(infoRequest);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Cập nhật hạn mức thẻ tín dụng")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping(value = "update-limit-payment")
    public ResponseEntity<?> updateLimitPayment(@RequestBody UpdateLimitPaymentREQDTO infoRequest) {
        try {
            ResponseModel responseModel = cardService.updateLimitPayment(infoRequest);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Lấy thông tin chi tiết khoảng trả góp")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping(value = "get-info-dpp")
    public ResponseEntity<?> getInfoDpp(@RequestBody GetInfoDPPREQDTO infoRequest) {
        try {
            ResponseModel responseModel = getInfoDPPService.getInfoDPP(infoRequest);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Lấy thông tin phí chuyển đổi các giao dịch trả góp")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping(value = "get-fee-dpp")
    public ResponseEntity<?> getFeeDPP(@RequestBody GetFeeDPPREQDTO infoRequest) {
        try {
            ResponseModel responseModel = getFeeDPPService.getFeeDPP(infoRequest);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Cập nhật thông tin liên kết TKTT và lấy thông tin danh sách các tài khoản liên kết với thẻ chính/phụ")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping(value = "update-link-acc")
    public ResponseEntity<?> updateLinkAcc(@RequestBody UpdateLinkAccREQDTO infoRequest) {
        try {
            ResponseModel responseModel = updateLinkAccService.updateLinkAcc(infoRequest);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Hàm lấy danh sách giao dịch thẻ được đăng ký trả góp")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping(value = "get-list-trans")
    public ResponseEntity<?> getListTrans(@RequestBody GetListTransREQDTO infoRequest) {
        try {
            ResponseModel responseModel = getListTransService.getListTrans(infoRequest);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Hàm lấy thông tin các giao dịch đã/đang trả góp")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping(value = "get-list-dpp")
    public ResponseEntity<?> getListDPP(@RequestBody GetListDPPREQDTO infoRequest) {
        try {
            ResponseModel responseModel = listDPPService.getListDPP(infoRequest);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Hàm lấy thông tin thẻ tín dụng")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping(value = "credit-card-info")
    public ResponseEntity<?> creditCardInfo(@RequestBody CreditCardInfoREQDTO infoRequest) {
        try {
            ResponseModel responseModel = creditCardInfoService.getCreditCard(infoRequest);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Hàm chấm dứt trả góp")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping(value = "cancel-dpp")
    public ResponseEntity<?> cancelDpp(@RequestBody CancelDppREQDTO infoRequest) {
        try {
            ResponseModel responseModel = cancelDppService.cancelDpp(infoRequest);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Hàm kiểm tra KH có lịch sử trả nợ tốt")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping(value = "check-late-dat-card-by-cif")
    public ResponseEntity<?> checkLateDatCardByCif(@RequestBody CheckLateDateCardByCifREQDTO infoRequest) {
        try {
            ResponseModel responseModel = checkLateDateCardByCifService.checkLateDateCardByCif(infoRequest);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(description = "Hàm lấy hạn mức giao dịch thẻ")
    @ApiResponses({
        @ApiResponse(responseCode = "00", description = "Thành công", content = @Content),
        @ApiResponse(responseCode = "99", description = "Lỗi hệ thống", content = @Content)
    })
    @PostMapping(value = "get-card-tran-limit")
    public ResponseEntity<?> getCardTranLimit(@RequestBody GetCardTranLimitREQDTO infoRequest) {
        try {
            ResponseModel responseModel = getCardTranLimitService.getCardTranLimit(infoRequest);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }
}
