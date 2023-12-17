package org.interswitch.bookstore.validators;

import com.google.common.io.Files;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;
import org.interswitch.bookstore.annotation.ValidateMultipart;
import org.interswitch.bookstore.exception.CustomBindRuntimeException;
import org.interswitch.bookstore.handlers.ErrorResponseWithArgsDto;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public class MultipartValidator implements ConstraintValidator<ValidateMultipart, MultipartFile> {

    private ValidateMultipart validateMultipart;

    @SneakyThrows
    public static boolean isValidFile(ValidateMultipart validateMultipart, MultipartFile multipartFile, boolean throwException) {
        ArrayList<ErrorResponseWithArgsDto.ErrorWithArguments> errorWithArguments = new ArrayList<>();
        String name = multipartFile.getOriginalFilename();
        if (name == null) {
            name = "";
        }
        String extension = Files.getFileExtension(name);

        if (!StringUtils.hasText(name) ||
                (validateMultipart.maxFileName() != -1 && name.length() > validateMultipart.maxFileName())) {
            errorWithArguments.add(new ErrorResponseWithArgsDto.ErrorWithArguments("invalid.file.name",
                    new Object[]{name}));
        }

        if (!StringUtils.hasText(extension) || !validateMultipart.extensions().contains(extension)) {
            errorWithArguments.add(new ErrorResponseWithArgsDto.ErrorWithArguments("invalid.file.format",
                    new Object[]{name}));
        }

        if (validateMultipart.maxSize() < multipartFile.getSize()) {
            errorWithArguments.add(new ErrorResponseWithArgsDto.ErrorWithArguments("invalid.file.size",
                    new Object[]{name, validateMultipart.maxSize()}));
        }

        if (!errorWithArguments.isEmpty() && throwException)
            throw new CustomBindRuntimeException(errorWithArguments
                    .toArray(new ErrorResponseWithArgsDto.ErrorWithArguments[]{}));

        return true;
    }

    @Override
    public void initialize(ValidateMultipart constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        validateMultipart = constraintAnnotation;
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return isValidFile(validateMultipart, value, false);
    }
}
