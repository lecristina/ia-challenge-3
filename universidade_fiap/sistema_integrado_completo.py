#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
SISTEMA INTEGRADO COMPLETO - FUNCIONANDO PERFEITAMENTE!
Sistema completo com identificação clara das motos e integração total
"""

import json
import time
import datetime
import random
import webbrowser
from http.server import HTTPServer, BaseHTTPRequestHandler
import threading
import sqlite3
import os

class SistemaIntegradoCompleto:
    def __init__(self):
        self.port = 8080
        self.server = None
        self.db_path = "sistema_motos_integrado.db"
        self.init_database()
        
    def init_database(self):
        """Inicializa banco de dados SQLite integrado"""
        conn = sqlite3.connect(self.db_path)
        cursor = conn.cursor()
        
        # Cria tabela de detecções
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS detection_event (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                event_type TEXT NOT NULL,
                x INTEGER NOT NULL,
                y INTEGER NOT NULL,
                timestamp TEXT NOT NULL,
                placa TEXT,
                status TEXT,
                zona TEXT,
                moto_id INTEGER
            )
        ''')
        
        # Cria tabela de motos (simulando o banco real)
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS moto (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                placa TEXT UNIQUE NOT NULL,
                status TEXT NOT NULL,
                zona_atual TEXT,
                cor TEXT,
                created_at TEXT DEFAULT CURRENT_TIMESTAMP
            )
        ''')
        
        # Insere motos iniciais do banco com cores específicas
        motos_iniciais = [
            ("ABC-1234", "DISPONIVEL", "estacionamento", "#007bff"),
            ("DEF-5678", "EM_USO", "entrada", "#28a745"),
            ("GHI-9012", "MANUTENCAO", "manutencao", "#ffc107"),
            ("JKL-3456", "ALUGADA", "entrada", "#28a745"),
            ("MNO-7890", "PRONTA", "estacionamento", "#007bff"),
            ("PQR-2468", "DISPONIVEL", "estacionamento", "#007bff"),
            ("STU-1357", "MANUTENCAO", "manutencao", "#ffc107"),
            ("VWX-9753", "EM_USO", "entrada", "#28a745")
        ]
        
        for placa, status, zona, cor in motos_iniciais:
            cursor.execute('''
                INSERT OR IGNORE INTO moto (placa, status, zona_atual, cor) 
                VALUES (?, ?, ?, ?)
            ''', (placa, status, zona, cor))
        
        conn.commit()
        conn.close()
        print("✅ Banco de dados integrado inicializado!")
    
    def adicionar_deteccao(self, placa, zona, x, y, event_type, moto_id=None):
        """Adiciona detecção no banco com ID da moto"""
        conn = sqlite3.connect(self.db_path)
        cursor = conn.cursor()
        
        cursor.execute('''
            INSERT INTO detection_event (event_type, x, y, timestamp, placa, status, zona, moto_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        ''', (event_type, x, y, datetime.datetime.now().isoformat(), placa, "DISPONIVEL", zona, moto_id))
        
        conn.commit()
        conn.close()
        
        print(f"✅ Detecção {placa} salva no banco em ({x}, {y}) - {zona.upper()}")
    
    def obter_deteccoes_recentes(self):
        """Obtém detecções recentes do banco com dados da moto"""
        conn = sqlite3.connect(self.db_path)
        cursor = conn.cursor()
        
        cursor.execute('''
            SELECT de.*, m.status, m.cor 
            FROM detection_event de
            LEFT JOIN moto m ON de.moto_id = m.id
            ORDER BY de.timestamp DESC 
            LIMIT 20
        ''')
        
        deteccoes = []
        for row in cursor.fetchall():
            deteccoes.append({
                "id": row[0],
                "eventType": row[1],
                "x": row[2],
                "y": row[3],
                "timestamp": row[4],
                "placa": row[5],
                "status": row[6],
                "zona": row[7],
                "moto_id": row[8],
                "moto_status": row[9],
                "cor": row[10] or "#ff4444"
            })
        
        conn.close()
        return deteccoes
    
    def obter_motos_banco(self):
        """Obtém motos do banco com cores"""
        conn = sqlite3.connect(self.db_path)
        cursor = conn.cursor()
        
        cursor.execute('SELECT * FROM moto')
        
        motos = []
        for row in cursor.fetchall():
            motos.append({
                "id": row[0],
                "placa": row[1],
                "status": row[2],
                "zona_atual": row[3],
                "cor": row[4],
                "created_at": row[5]
            })
        
        conn.close()
        return motos
    
    def obter_posicao_zona(self, zona):
        """Calcula posição na zona"""
        if zona == "entrada":
            return random.randint(20, 190), random.randint(20, 70)
        elif zona == "saida":
            return random.randint(440, 620), random.randint(20, 70)
        elif zona == "estacionamento":
            return random.randint(20, 290), random.randint(360, 460)
        elif zona == "manutencao":
            return random.randint(450, 630), random.randint(360, 460)
        else:
            return random.randint(50, 590), random.randint(50, 430)
    
    def criar_html_dashboard_integrado(self):
        """Cria HTML do dashboard totalmente integrado"""
        motos_banco = self.obter_motos_banco()
        deteccoes = self.obter_deteccoes_recentes()
        
        html = f"""
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Integrado - Sistema de Motos</title>
    <style>
        body {{
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f0f0f0;
        }}
        .container {{
            max-width: 1400px;
            margin: 0 auto;
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }}
        .header {{
            text-align: center;
            margin-bottom: 30px;
            padding: 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 10px;
        }}
        .stats {{
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
            margin: 20px 0;
        }}
        .stat-card {{
            background: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            text-align: center;
            border-left: 4px solid #007bff;
            transition: transform 0.3s ease;
        }}
        .stat-card:hover {{
            transform: translateY(-5px);
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }}
        .stat-number {{
            font-size: 32px;
            font-weight: bold;
            color: #007bff;
        }}
        .stat-label {{
            color: #666;
            margin-top: 5px;
            font-size: 14px;
        }}
        .mapa {{
            position: relative;
            width: 640px;
            height: 480px;
            margin: 20px auto;
            border: 3px solid #333;
            background: #e8f5e8;
            border-radius: 10px;
            overflow: hidden;
        }}
        .zona {{
            position: absolute;
            border: 2px dashed #666;
            background: rgba(255,255,255,0.5);
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            color: #333;
            text-align: center;
            font-size: 12px;
        }}
        .entrada {{ 
            top: 10px; left: 10px; width: 200px; height: 80px; 
            border-color: #28a745; background: rgba(40, 167, 69, 0.2);
        }}
        .saida {{ 
            top: 10px; right: 10px; width: 200px; height: 80px; 
            border-color: #dc3545; background: rgba(220, 53, 69, 0.2);
        }}
        .estacionamento {{ 
            bottom: 10px; left: 10px; width: 300px; height: 120px; 
            border-color: #007bff; background: rgba(0, 123, 255, 0.2);
        }}
        .manutencao {{ 
            bottom: 10px; right: 10px; width: 200px; height: 120px; 
            border-color: #ffc107; background: rgba(255, 193, 7, 0.2);
        }}
        .moto {{
            position: absolute;
            width: 20px;
            height: 20px;
            border-radius: 50%;
            border: 3px solid white;
            box-shadow: 0 0 15px rgba(0,0,0,0.5);
            cursor: pointer;
            z-index: 10;
            animation: pulse 1s infinite;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 10px;
            font-weight: bold;
            color: white;
            text-shadow: 1px 1px 2px rgba(0,0,0,0.8);
        }}
        .moto.entrada {{ background: #28a745; }}
        .moto.saida {{ background: #dc3545; }}
        .moto.estacionamento {{ background: #007bff; }}
        .moto.manutencao {{ background: #ffc107; }}
        .moto-info {{
            position: absolute;
            background: rgba(0,0,0,0.9);
            color: white;
            padding: 8px 12px;
            border-radius: 8px;
            font-size: 12px;
            white-space: nowrap;
            z-index: 20;
            display: none;
            box-shadow: 0 4px 12px rgba(0,0,0,0.3);
            max-width: 200px;
        }}
        .coordenadas {{
            position: absolute;
            bottom: 5px;
            left: 5px;
            background: rgba(0,0,0,0.8);
            color: white;
            padding: 5px 10px;
            border-radius: 5px;
            font-size: 12px;
        }}
        .controles {{
            text-align: center;
            margin: 20px 0;
        }}
        .btn {{
            padding: 12px 24px;
            margin: 5px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-weight: bold;
            font-size: 14px;
            transition: all 0.3s ease;
        }}
        .btn:hover {{
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.2);
        }}
        .btn-primary {{ background: #007bff; color: white; }}
        .btn-success {{ background: #28a745; color: white; }}
        .btn-danger {{ background: #dc3545; color: white; }}
        .btn-warning {{ background: #ffc107; color: black; }}
        .btn-info {{ background: #17a2b8; color: white; }}
        .tabela-motos {{
            margin-top: 30px;
            overflow-x: auto;
        }}
        .tabela-motos table {{
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }}
        .tabela-motos th, .tabela-motos td {{
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }}
        .tabela-motos th {{
            background-color: #f8f9fa;
            font-weight: bold;
        }}
        .tabela-motos tr:hover {{
            background-color: #f5f5f5;
        }}
        .legenda {{
            display: flex;
            justify-content: center;
            gap: 20px;
            margin: 20px 0;
            flex-wrap: wrap;
        }}
        .legenda-item {{
            display: flex;
            align-items: center;
            gap: 8px;
            padding: 8px 16px;
            background: #f8f9fa;
            border-radius: 20px;
            border: 1px solid #dee2e6;
        }}
        .legenda-cor {{
            width: 20px;
            height: 20px;
            border-radius: 50%;
        }}
        @keyframes pulse {{
            0% {{ transform: scale(1); }}
            50% {{ transform: scale(1.2); }}
            100% {{ transform: scale(1); }}
        }}
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🏍️ Sistema Integrado de Motos - FUNCIONANDO PERFEITAMENTE!</h1>
            <p>Sistema completo com identificação clara das motos e integração total</p>
        </div>
        
        <div class="legenda">
            <div class="legenda-item">
                <div class="legenda-cor" style="background: #28a745;"></div>
                <span>Entrada (Verde)</span>
            </div>
            <div class="legenda-item">
                <div class="legenda-cor" style="background: #dc3545;"></div>
                <span>Saída (Vermelho)</span>
            </div>
            <div class="legenda-item">
                <div class="legenda-cor" style="background: #007bff;"></div>
                <span>Estacionamento (Azul)</span>
            </div>
            <div class="legenda-item">
                <div class="legenda-cor" style="background: #ffc107;"></div>
                <span>Manutenção (Amarelo)</span>
            </div>
        </div>
        
        <div class="stats">
            <div class="stat-card">
                <div class="stat-number" id="total-motos">{len(motos_banco)}</div>
                <div class="stat-label">Total de Motos no Banco</div>
            </div>
            <div class="stat-card">
                <div class="stat-number" id="total-deteccoes">{len(deteccoes)}</div>
                <div class="stat-label">Detecções Recentes</div>
            </div>
            <div class="stat-card">
                <div class="stat-number" id="entrada">0</div>
                <div class="stat-label">Na Entrada</div>
            </div>
            <div class="stat-card">
                <div class="stat-number" id="saida">0</div>
                <div class="stat-label">Na Saída</div>
            </div>
            <div class="stat-card">
                <div class="stat-number" id="estacionamento">0</div>
                <div class="stat-label">Estacionadas</div>
            </div>
            <div class="stat-card">
                <div class="stat-number" id="manutencao">0</div>
                <div class="stat-label">Em Manutenção</div>
            </div>
        </div>
        
        <div class="controles">
            <button class="btn btn-primary" onclick="carregarMotos()">🔄 Carregar do Banco</button>
            <button class="btn btn-success" onclick="adicionarMotoBanco()">➕ Adicionar Moto</button>
            <button class="btn btn-warning" onclick="simularDeteccao()">🎯 Simular Detecção</button>
            <button class="btn btn-info" onclick="mostrarTodasMotos()">👁️ Mostrar Todas as Motos</button>
            <button class="btn btn-danger" onclick="limparMapa()">🗑️ Limpar Mapa</button>
        </div>
        
        <div class="mapa" id="mapa">
            <div class="zona entrada">
                ENTRADA<br>
                <small>(10,10)-(210,90)</small>
            </div>
            <div class="zona saida">
                SAÍDA<br>
                <small>(430,10)-(630,90)</small>
            </div>
            <div class="zona estacionamento">
                ESTACIONAMENTO<br>
                <small>(10,350)-(310,470)</small>
            </div>
            <div class="zona manutencao">
                MANUTENÇÃO<br>
                <small>(440,350)-(640,470)</small>
            </div>
            <div class="coordenadas" id="coordenadas">X: 0, Y: 0</div>
        </div>
        
        <div class="tabela-motos">
            <h3>📋 Motos do Banco de Dados</h3>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Placa</th>
                        <th>Status</th>
                        <th>Zona Atual</th>
                        <th>Cor</th>
                        <th>Criado em</th>
                    </tr>
                </thead>
                <tbody id="tabela-motos">
                    {self.criar_tabela_motos(motos_banco)}
                </tbody>
            </table>
        </div>
    </div>

    <script>
        let motos = [];
        
        function carregarMotos() {{
            console.log("🔄 Carregando motos do banco...");
            
            fetch('/api/detections/recent')
                .then(response => response.json())
                .then(data => {{
                    console.log(`✅ ${{data.length}} detecções carregadas`);
                    mostrarMotos(data);
                }})
                .catch(error => {{
                    console.error("❌ Erro:", error);
                }});
        }}
        
        function mostrarMotos(eventos) {{
            // Limpa motos existentes
            document.querySelectorAll('.moto').forEach(moto => moto.remove());
            motos = [];
            
            // Adiciona cada moto
            eventos.forEach(evento => {{
                adicionarMoto(evento);
            }});
            
            atualizarStats();
        }}
        
        function adicionarMoto(evento) {{
            const moto = document.createElement('div');
            moto.className = 'moto';
            
            // Posição
            moto.style.left = evento.x + 'px';
            moto.style.top = evento.y + 'px';
            
            // Cor baseada na zona
            const zona = evento.zona || determinarZona(evento.x, evento.y);
            moto.classList.add(zona);
            
            // Usa cor específica se disponível
            if (evento.cor) {{
                moto.style.background = evento.cor;
            }}
            
            // Texto da placa (primeiras 3 letras)
            moto.textContent = evento.placa ? evento.placa.substring(0, 3) : 'MOT';
            
            // Tooltip detalhado
            moto.title = `${{evento.placa || 'Moto'}} - ${{zona}} - (${{evento.x}}, ${{evento.y}}) - Status: ${{evento.moto_status || 'N/A'}}`;
            
            // Adiciona info tooltip
            const info = document.createElement('div');
            info.className = 'moto-info';
            info.innerHTML = `
                <strong>${{evento.placa || 'Moto'}}</strong><br>
                Zona: ${{zona}}<br>
                Status: ${{evento.moto_status || 'N/A'}}<br>
                Pos: (${{evento.x}}, ${{evento.y}})<br>
                Evento: ${{evento.eventType || 'N/A'}}
            `;
            moto.appendChild(info);
            
            // Eventos do mouse
            moto.addEventListener('mouseenter', function() {{
                info.style.display = 'block';
                info.style.left = (evento.x + 25) + 'px';
                info.style.top = (evento.y - 10) + 'px';
            }});
            
            moto.addEventListener('mouseleave', function() {{
                info.style.display = 'none';
            }});
            
            // Adiciona ao mapa
            document.getElementById('mapa').appendChild(moto);
            motos.push({{...evento, zona}});
            
            console.log(`✅ Moto ${{evento.placa}} adicionada em (${{evento.x}}, ${{evento.y}}) - ${{zona}}`);
        }}
        
        function determinarZona(x, y) {{
            if (x >= 10 && x <= 210 && y >= 10 && y <= 90) return 'entrada';
            if (x >= 430 && x <= 630 && y >= 10 && y <= 90) return 'saida';
            if (x >= 10 && x <= 310 && y >= 350 && y <= 470) return 'estacionamento';
            if (x >= 440 && x <= 640 && y >= 350 && y <= 470) return 'manutencao';
            return 'entrada';
        }}
        
        function atualizarStats() {{
            const stats = {{
                entrada: 0,
                saida: 0,
                estacionamento: 0,
                manutencao: 0
            }};
            
            motos.forEach(moto => {{
                stats[moto.zona]++;
            }});
            
            document.getElementById('entrada').textContent = stats.entrada;
            document.getElementById('saida').textContent = stats.saida;
            document.getElementById('estacionamento').textContent = stats.estacionamento;
            document.getElementById('manutencao').textContent = stats.manutencao;
        }}
        
        function adicionarMotoBanco() {{
            const placa = prompt("Digite a placa da moto:");
            if (!placa) return;
            
            const zonas = ['entrada', 'saida', 'estacionamento', 'manutencao'];
            const zona = zonas[Math.floor(Math.random() * zonas.length)];
            
            let x, y;
            if (zona === 'entrada') {{
                x = Math.random() * 180 + 20;
                y = Math.random() * 50 + 20;
            }} else if (zona === 'saida') {{
                x = Math.random() * 180 + 440;
                y = Math.random() * 50 + 20;
            }} else if (zona === 'estacionamento') {{
                x = Math.random() * 270 + 20;
                y = Math.random() * 100 + 360;
            }} else if (zona === 'manutencao') {{
                x = Math.random() * 180 + 450;
                y = Math.random() * 100 + 360;
            }}
            
            const moto = {{
                placa: placa,
                zona: zona,
                x: Math.round(x),
                y: Math.round(y),
                eventType: `moto_${{zona}}`
            }};
            
            // Envia para API
            fetch('/api/detections', {{
                method: 'POST',
                headers: {{'Content-Type': 'application/json'}},
                body: JSON.stringify(moto)
            }}).then(() => {{
                adicionarMoto(moto);
                atualizarStats();
                carregarMotos(); // Recarrega do banco
            }});
        }}
        
        function simularDeteccao() {{
            const motosBanco = {json.dumps(motos_banco)};
            const moto = motosBanco[Math.floor(Math.random() * motosBanco.length)];
            
            let x, y;
            if (moto.zona_atual === 'entrada') {{
                x = Math.random() * 180 + 20;
                y = Math.random() * 50 + 20;
            }} else if (moto.zona_atual === 'saida') {{
                x = Math.random() * 180 + 440;
                y = Math.random() * 50 + 20;
            }} else if (moto.zona_atual === 'estacionamento') {{
                x = Math.random() * 270 + 20;
                y = Math.random() * 100 + 360;
            }} else if (moto.zona_atual === 'manutencao') {{
                x = Math.random() * 180 + 450;
                y = Math.random() * 100 + 360;
            }}
            
            const deteccao = {{
                placa: moto.placa,
                zona: moto.zona_atual,
                x: Math.round(x),
                y: Math.round(y),
                eventType: `moto_${{moto.zona_atual}}`,
                cor: moto.cor,
                moto_status: moto.status
            }};
            
            // Envia para API
            fetch('/api/detections', {{
                method: 'POST',
                headers: {{'Content-Type': 'application/json'}},
                body: JSON.stringify(deteccao)
            }}).then(() => {{
                adicionarMoto(deteccao);
                atualizarStats();
                carregarMotos(); // Recarrega do banco
            }});
        }}
        
        function mostrarTodasMotos() {{
            const motosBanco = {json.dumps(motos_banco)};
            
            // Limpa mapa
            document.querySelectorAll('.moto').forEach(moto => moto.remove());
            motos = [];
            
            // Adiciona todas as motos do banco
            motosBanco.forEach(moto => {{
                let x, y;
                if (moto.zona_atual === 'entrada') {{
                    x = Math.random() * 180 + 20;
                    y = Math.random() * 50 + 20;
                }} else if (moto.zona_atual === 'saida') {{
                    x = Math.random() * 180 + 440;
                    y = Math.random() * 50 + 20;
                }} else if (moto.zona_atual === 'estacionamento') {{
                    x = Math.random() * 270 + 20;
                    y = Math.random() * 100 + 360;
                }} else if (moto.zona_atual === 'manutencao') {{
                    x = Math.random() * 180 + 450;
                    y = Math.random() * 100 + 360;
                }}
                
                const deteccao = {{
                    placa: moto.placa,
                    zona: moto.zona_atual,
                    x: Math.round(x),
                    y: Math.round(y),
                    eventType: `moto_${{moto.zona_atual}}`,
                    cor: moto.cor,
                    moto_status: moto.status
                }};
                
                adicionarMoto(deteccao);
            }});
            
            atualizarStats();
        }}
        
        function limparMapa() {{
            document.querySelectorAll('.moto').forEach(moto => moto.remove());
            motos = [];
            atualizarStats();
        }}
        
        // Coordenadas do mouse
        document.getElementById('mapa').addEventListener('mousemove', function(e) {{
            const rect = this.getBoundingClientRect();
            const x = Math.round(e.clientX - rect.left);
            const y = Math.round(e.clientY - rect.top);
            document.getElementById('coordenadas').textContent = `X: ${{x}}, Y: ${{y}}`;
        }});
        
        // Inicia automaticamente
        window.onload = function() {{
            console.log("🚀 Sistema integrado carregado!");
            carregarMotos();
        }};
    </script>
</body>
</html>
        """
        return html
    
    def criar_tabela_motos(self, motos):
        """Cria HTML da tabela de motos com cores"""
        html = ""
        for moto in motos:
            html += f"""
            <tr>
                <td>{moto['id']}</td>
                <td>{moto['placa']}</td>
                <td>{moto['status']}</td>
                <td>{moto['zona_atual']}</td>
                <td><div style="width: 20px; height: 20px; background: {moto['cor']}; border-radius: 50%; display: inline-block;"></div></td>
                <td>{moto['created_at']}</td>
            </tr>
            """
        return html
    
    def criar_servidor(self):
        """Cria servidor HTTP integrado com banco"""
        class Handler(BaseHTTPRequestHandler):
            def do_GET(self):
                if self.path == '/':
                    self.send_response(200)
                    self.send_header('Content-type', 'text/html; charset=utf-8')
                    self.end_headers()
                    self.wfile.write(sistema.criar_html_dashboard_integrado().encode('utf-8'))
                elif self.path == '/api/detections/recent':
                    self.send_response(200)
                    self.send_header('Content-type', 'application/json')
                    self.end_headers()
                    deteccoes = sistema.obter_deteccoes_recentes()
                    self.wfile.write(json.dumps(deteccoes).encode('utf-8'))
                elif self.path == '/api/detections/count':
                    self.send_response(200)
                    self.send_header('Content-type', 'application/json')
                    self.end_headers()
                    deteccoes = sistema.obter_deteccoes_recentes()
                    self.wfile.write(str(len(deteccoes)).encode('utf-8'))
                else:
                    self.send_response(404)
                    self.end_headers()
            
            def do_POST(self):
                if self.path == '/api/detections':
                    content_length = int(self.headers['Content-Length'])
                    post_data = self.rfile.read(content_length)
                    data = json.loads(post_data.decode('utf-8'))
                    
                    sistema.adicionar_deteccao(
                        data.get('placa', 'MOTO-001'),
                        data.get('zona', 'entrada'),
                        data.get('x', 100),
                        data.get('y', 100),
                        data.get('eventType', 'moto_detectada'),
                        data.get('moto_id')
                    )
                    
                    self.send_response(200)
                    self.send_header('Content-type', 'application/json')
                    self.end_headers()
                    self.wfile.write(json.dumps({"status": "success"}).encode('utf-8'))
                else:
                    self.send_response(404)
                    self.end_headers()
        
        self.server = HTTPServer(('localhost', self.port), Handler)
        return self.server
    
    def iniciar_sistema(self):
        """Inicia o sistema totalmente integrado"""
        print("🔥 INICIANDO SISTEMA INTEGRADO COMPLETO!")
        print("=" * 60)
        
        # Cria servidor
        server = self.criar_servidor()
        
        # Inicia servidor em thread separada
        def run_server():
            print(f"🚀 Servidor iniciado em http://localhost:{self.port}")
            server.serve_forever()
        
        thread = threading.Thread(target=run_server)
        thread.daemon = True
        thread.start()
        
        # Aguarda um pouco
        time.sleep(2)
        
        # Adiciona detecções iniciais
        print("\n🎯 Adicionando detecções iniciais...")
        motos_banco = self.obter_motos_banco()
        
        for moto in motos_banco[:5]:  # Adiciona 5 detecções iniciais
            x, y = self.obter_posicao_zona(moto['zona_atual'])
            self.adicionar_deteccao(
                moto['placa'], 
                moto['zona_atual'], 
                x, y, 
                f"moto_{moto['zona_atual']}",
                moto['id']
            )
            time.sleep(0.5)
        
        print(f"\n✅ Sistema integrado funcionando! {len(motos_banco)} motos no banco!")
        print(f"🌐 Acesse: http://localhost:{self.port}")
        
        # Abre navegador
        webbrowser.open(f'http://localhost:{self.port}')
        
        return server

def main():
    """Função principal"""
    global sistema
    sistema = SistemaIntegradoCompleto()
    
    try:
        server = sistema.iniciar_sistema()
        
        print("\n🎉 SISTEMA INTEGRADO FUNCIONANDO PERFEITAMENTE!")
        print("📋 Funcionalidades:")
        print("✅ Identificação clara das motos (placa nas bolinhas)")
        print("✅ Integração total com banco de dados")
        print("✅ Cores específicas por zona")
        print("✅ Tooltips com informações detalhadas")
        print("✅ Status das motos visível")
        print("✅ Sistema completo e funcional")
        
        print("\n🎯 CONTROLES:")
        print("• 'Carregar do Banco' - carrega detecções do banco")
        print("• 'Adicionar Moto' - adiciona nova moto")
        print("• 'Simular Detecção' - simula detecção de moto do banco")
        print("• 'Mostrar Todas as Motos' - mostra todas as motos do banco")
        print("• Passe o mouse sobre as bolinhas para ver detalhes")
        
        print("\n⏹️ Pressione Ctrl+C para parar")
        
        # Mantém o sistema rodando
        while True:
            time.sleep(1)
            
    except KeyboardInterrupt:
        print("\n\n👋 Sistema parado pelo usuário")
        if sistema.server:
            sistema.server.shutdown()

if __name__ == "__main__":
    main()
