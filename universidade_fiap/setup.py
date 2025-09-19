#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script de configuração inicial do projeto
"""

import os
import sys
import subprocess
import platform

def check_python_version():
    """Verifica a versão do Python"""
    if sys.version_info < (3, 8):
        print("❌ Python 3.8+ é necessário!")
        print(f"Versão atual: {sys.version}")
        return False
    print(f"✅ Python {sys.version.split()[0]} detectado")
    return True

def check_java():
    """Verifica se o Java está instalado"""
    try:
        result = subprocess.run(['java', '-version'], capture_output=True, text=True)
        if result.returncode == 0:
            print("✅ Java detectado")
            return True
    except FileNotFoundError:
        pass
    
    print("❌ Java não encontrado!")
    print("💡 Instale o Java 17+ para executar o Spring Boot")
    return False

def check_maven():
    """Verifica se o Maven está disponível"""
    try:
        result = subprocess.run(['mvn', '--version'], capture_output=True, text=True)
        if result.returncode == 0:
            print("✅ Maven detectado")
            return True
    except FileNotFoundError:
        pass
    
    print("⚠️ Maven não encontrado, mas o projeto tem mvnw")
    return True

def install_python_dependencies():
    """Instala as dependências Python"""
    print("🔄 Instalando dependências Python...")
    try:
        subprocess.run([sys.executable, '-m', 'pip', 'install', '-r', 'requirements.txt'], check=True)
        print("✅ Dependências Python instaladas")
        return True
    except subprocess.CalledProcessError as e:
        print(f"❌ Erro ao instalar dependências Python: {e}")
        return False

def download_yolo_files():
    """Baixa os arquivos YOLO"""
    print("🔄 Baixando arquivos YOLO...")
    try:
        subprocess.run([sys.executable, 'download_yolo.py'], check=True)
        print("✅ Arquivos YOLO baixados")
        return True
    except subprocess.CalledProcessError as e:
        print(f"❌ Erro ao baixar arquivos YOLO: {e}")
        return False

def check_database_connection():
    """Verifica conexão com o banco de dados"""
    print("🔄 Verificando conexão com MySQL...")
    try:
        import pymysql
        connection = pymysql.connect(
            host='localhost',
            user='root',
            password='230205',
            database='trackzone_mvc2'
        )
        connection.close()
        print("✅ Conexão com MySQL OK")
        return True
    except ImportError:
        print("⚠️ PyMySQL não instalado, pulando verificação do banco")
        return True
    except Exception as e:
        print(f"❌ Erro ao conectar com MySQL: {e}")
        print("💡 Certifique-se que o MySQL está rodando e a senha está correta")
        return False

def create_directories():
    """Cria diretórios necessários"""
    dirs = ['logs', 'temp']
    for dir_name in dirs:
        if not os.path.exists(dir_name):
            os.makedirs(dir_name)
            print(f"📁 Diretório criado: {dir_name}")

def main():
    """Função principal"""
    print("🚀 Configuração do Projeto Mottu")
    print("=" * 50)
    
    # Verificações básicas
    checks = [
        ("Python 3.8+", check_python_version),
        ("Java", check_java),
        ("Maven", check_maven),
    ]
    
    all_ok = True
    for name, check_func in checks:
        if not check_func():
            all_ok = False
    
    if not all_ok:
        print("\n❌ Algumas verificações falharam. Corrija os problemas antes de continuar.")
        return
    
    # Instalação de dependências
    print("\n📦 Instalando dependências...")
    if not install_python_dependencies():
        print("❌ Falha na instalação das dependências Python")
        return
    
    # Download dos arquivos YOLO
    print("\n📥 Baixando arquivos YOLO...")
    if not download_yolo_files():
        print("⚠️ Falha no download dos arquivos YOLO")
        print("💡 Execute manualmente: python download_yolo.py")
    
    # Verificação do banco
    print("\n🗄️ Verificando banco de dados...")
    check_database_connection()
    
    # Criação de diretórios
    print("\n📁 Criando diretórios...")
    create_directories()
    
    print("\n✅ Configuração concluída!")
    print("\n📋 Próximos passos:")
    print("1. Baixe manualmente o yolov3.weights (248MB):")
    print("   https://pjreddie.com/media/files/yolov3.weights")
    print("2. Execute o Spring Boot:")
    print("   ./mvnw spring-boot:run")
    print("3. Execute o detector:")
    print("   python detector.py")
    print("4. Acesse: http://localhost:8080/dashboard")

if __name__ == "__main__":
    main()
