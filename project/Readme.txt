Quang_dev
+ Cấu hình thêm i18n- đa ngôn ngữ, dùng để đưa những message về file cấu hình
Use:
+ config/MvcConfig.java
+ Folder messages resources
+ controller/api/AuthRestController:
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO user,  BindingResult bindingResult) {
        System.out.println(messageSource.getMessage("model.userdto.email.invalid",null, new Locale("vi")));
Ref:
https://www.javadevjournal.com/spring-boot/spring-custom-validation-message-source/
https://stackjava.com/spring/code-vi-du-spring-boot-da-ngon-ngu-internationalization-i18n.html