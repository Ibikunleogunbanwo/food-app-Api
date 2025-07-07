package com.Afrochow.food_app.pojo;
import lombok.Data;

import static com.Afrochow.food_app.config.AppConstant.*;

@Data
public class BaseResponse {
    private String statusCode;
    private String message;
    private Object data;


    public BaseResponse() {

    }

    public BaseResponse(boolean error) {
        this.statusCode = ERROR_STATUS_CODE;
        this.message = ERROR_MESSAGE;
        this.data = EMPTY_DATA;
    }
}
