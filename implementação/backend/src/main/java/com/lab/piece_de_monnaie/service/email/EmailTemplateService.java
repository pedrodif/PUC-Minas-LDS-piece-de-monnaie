package com.lab.piece_de_monnaie.service.email;

import com.lab.piece_de_monnaie.dto.email.VoucherEmailData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailTemplateService {

    public String gerarTemplateEmailAluno(VoucherEmailData dados) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background-color: #4CAF50; color: white; padding: 20px; text-align: center; border-radius: 5px 5px 0 0; }
                    .content { background-color: #f9f9f9; padding: 30px; border-radius: 0 0 5px 5px; }
                    .voucher-code { background-color: #fff; border: 2px dashed #4CAF50; padding: 20px; text-align: center; margin: 20px 0; border-radius: 5px; }
                    .voucher-code h2 { color: #4CAF50; margin: 0; font-size: 28px; letter-spacing: 3px; }
                    .footer { text-align: center; margin-top: 20px; color: #666; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🎉 Vantagem Resgatada!</h1>
                    </div>
                    <div class="content">
                        <p>Olá <strong>%s</strong>,</p>
                        <p>Parabéns! Você resgatou com sucesso a vantagem:</p>
                        <p><strong>%s</strong></p>
                        <p>Seu código de voucher é:</p>
                        <div class="voucher-code">
                            <h2>%s</h2>
                        </div>
                        <p>Apresente este código na empresa parceira para utilizar sua vantagem.</p>
                        <p>Obrigado por usar o Piece de Monnaie!</p>
                    </div>
                    <div class="footer">
                        <p>Este é um email automático, por favor não responda.</p>
                    </div>
                </div>
            </body>
            </html>
            """, dados.getAlunoNome(), dados.getVantagemDescricao(), dados.getVoucherCode());
    }

    public String gerarTemplateEmailEmpresa(VoucherEmailData dados) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background-color: #2196F3; color: white; padding: 20px; text-align: center; border-radius: 5px 5px 0 0; }
                    .content { background-color: #f9f9f9; padding: 30px; border-radius: 0 0 5px 5px; }
                    .info-box { background-color: #fff; border-left: 4px solid #2196F3; padding: 15px; margin: 15px 0; }
                    .voucher-code { background-color: #fff; border: 2px dashed #2196F3; padding: 20px; text-align: center; margin: 20px 0; border-radius: 5px; }
                    .voucher-code h2 { color: #2196F3; margin: 0; font-size: 28px; letter-spacing: 3px; }
                    .footer { text-align: center; margin-top: 20px; color: #666; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>📧 Novo Resgate de Vantagem</h1>
                    </div>
                    <div class="content">
                        <p>Olá <strong>%s</strong>,</p>
                        <p>Um aluno resgatou uma de suas vantagens:</p>
                        <p><strong>%s</strong></p>
                        <div class="info-box">
                            <p><strong>Dados do Aluno:</strong></p>
                            <p>Nome: %s</p>
                            <p>Email: %s</p>
                        </div>
                        <p>O código do voucher para validação é:</p>
                        <div class="voucher-code">
                            <h2>%s</h2>
                        </div>
                        <p>Por favor, valide este código quando o aluno apresentar.</p>
                    </div>
                    <div class="footer">
                        <p>Este é um email automático, por favor não responda.</p>
                    </div>
                </div>
            </body>
            </html>
            """, dados.getEmpresaNome(), dados.getVantagemDescricao(), 
                dados.getAlunoNome(), dados.getAlunoEmail(), dados.getVoucherCode());
    }

    public String gerarAssuntoEmailAluno() {
        return "Voucher de Vantagem Resgatada - Piece de Monnaie";
    }

    public String gerarAssuntoEmailEmpresa() {
        return "Novo Resgate de Vantagem - Piece de Monnaie";
    }
}

