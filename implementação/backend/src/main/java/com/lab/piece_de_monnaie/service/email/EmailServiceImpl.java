package com.lab.piece_de_monnaie.service.email;

import com.lab.piece_de_monnaie.dto.email.VoucherEmailData;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final EmailTemplateService emailTemplateService;

    @Override
    public void enviarEmailVoucherAluno(VoucherEmailData dados) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(dados.getAlunoEmail());
            helper.setSubject(emailTemplateService.gerarAssuntoEmailAluno());
            helper.setText(emailTemplateService.gerarTemplateEmailAluno(dados), true);

            mailSender.send(message);
            log.info("Email de voucher enviado com sucesso para o aluno: {}", dados.getAlunoEmail());
        } catch (MessagingException e) {
            log.error("Erro ao enviar email de voucher para o aluno: {}", dados.getAlunoEmail(), e);
            throw new RuntimeException("Erro ao enviar email para o aluno", e);
        }
    }

    @Override
    public void enviarEmailVoucherEmpresa(VoucherEmailData dados) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(dados.getEmpresaEmail());
            helper.setSubject(emailTemplateService.gerarAssuntoEmailEmpresa());
            helper.setText(emailTemplateService.gerarTemplateEmailEmpresa(dados), true);

            mailSender.send(message);
            log.info("Email de voucher enviado com sucesso para a empresa: {}", dados.getEmpresaEmail());
        } catch (MessagingException e) {
            log.error("Erro ao enviar email de voucher para a empresa: {}", dados.getEmpresaEmail(), e);
            throw new RuntimeException("Erro ao enviar email para a empresa", e);
        }
    }
}

