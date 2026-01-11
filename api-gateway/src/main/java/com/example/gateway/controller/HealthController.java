package com.example.gateway.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class HealthController {

    @GetMapping(value = "/health.html", produces = MediaType.TEXT_HTML_VALUE)
    public String health() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>API Gateway Health</title>
                    <style>
                        body {
                            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            min-height: 100vh;
                            margin: 0;
                            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
                        }
                        .health-card {
                            background: white;
                            padding: 40px;
                            border-radius: 12px;
                            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
                            text-align: center;
                            max-width: 400px;
                        }
                        .status-icon {
                            font-size: 64px;
                            margin-bottom: 20px;
                        }
                        h1 {
                            color: #333;
                            margin: 0 0 10px 0;
                            font-size: 28px;
                        }
                        .service-name {
                            color: #00a8cc;
                            font-weight: bold;
                            font-size: 20px;
                            margin-bottom: 20px;
                        }
                        .status {
                            display: inline-block;
                            padding: 8px 20px;
                            background: #10b981;
                            color: white;
                            border-radius: 20px;
                            font-weight: bold;
                            margin: 10px 0;
                        }
                        .timestamp {
                            color: #666;
                            font-size: 14px;
                            margin-top: 20px;
                        }
                    </style>
                </head>
                <body>
                    <div class="health-card">
                        <div class="status-icon">✅</div>
                        <h1>Service Health Check</h1>
                        <div class="service-name">API Gateway</div>
                        <div class="status">HEALTHY</div>
                        <div class="timestamp">Last checked: """ + timestamp + """
                        </div>
                    </div>
                </body>
                </html>
                """;
    }
}
