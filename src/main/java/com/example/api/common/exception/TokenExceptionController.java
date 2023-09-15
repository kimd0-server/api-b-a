package com.example.api.common.exception;

import com.example.api.common.base.vo.Code;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/exception"})
public class TokenExceptionController {
    @GetMapping("/entrypoint")
    public void entryPoint() {
        throw new GeneralException(Code.UNAUTHORIZED);
    }
    @GetMapping("/access")
    public void denied() {
        throw new GeneralException(Code.UNAUTHORIZED);
    }
    @GetMapping("/{exception}")
    public void signatureException(@PathVariable(name = "exception") String exception) {
        switch (exception) {
            case "signature":
                throw new GeneralException(Code.TOKEN_SIGNATURE);
            case "malformed":
                throw new GeneralException(Code.TOKEN_MALFORMED);
            case "expired":
                throw new GeneralException(Code.TOKEN_EXPIRED);
            case "unsupported":
                throw new GeneralException(Code.TOKEN_UNSUPPORTED);
            case "illegal":
                throw new GeneralException(Code.TOKEN_ILLEGAL_ARGUMENT);
        }
    }
}