package com.example.api.auth.api.act;

import com.example.api.auth.api.biz.JoinService;
import com.example.api.auth.api.dto.JoinParamDTO;
import com.example.api.common.base.dto.DataResponseDTO;
import com.example.api.common.base.dto.ResponseDTO;
import com.example.api.common.base.vo.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/auth/join"})
public class JoinController {
    private final JoinService joinService;

    @PostMapping("/user")
    public ResponseDTO join(@Valid @RequestBody JoinParamDTO joinParamDTO, BindingResult bindingResult) {
        if (joinService.valid(joinParamDTO, bindingResult)) {
            return ResponseDTO.of(false, Code.VALIDATION_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }

        joinService.joinUser(joinParamDTO);

        return ResponseDTO.of(true, Code.OK);
    }

    @PostMapping("/admin")
    public ResponseDTO joinAdmin(@Valid @RequestBody JoinParamDTO joinParamDTO, BindingResult bindingResult) {
        if (joinService.valid(joinParamDTO, bindingResult)) {
            return ResponseDTO.of(false, Code.VALIDATION_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }

        joinService.joinAdmin(joinParamDTO);

        return ResponseDTO.of(true, Code.OK);
    }

    @GetMapping("/selEmailCheck")
    public DataResponseDTO joinIdCheck(@RequestParam(name = "userEmail") String email) {
        return DataResponseDTO.of(joinService.duplEmail(email));
    }

    @GetMapping("/selNickCheck")
    public DataResponseDTO joinNickCheck(@RequestParam(name = "userNick") String nick) {
        return DataResponseDTO.of(joinService.duplNick(nick));
    }
}
