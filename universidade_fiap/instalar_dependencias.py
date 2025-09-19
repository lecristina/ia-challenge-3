#!/usr/bin/env python3
"""
Script para instalar dependências Python necessárias
"""

import subprocess
import sys

def instalar_dependencias():
    """Instala as dependências Python necessárias"""
    print("🔧 Instalando dependências Python...")
    
    dependencias = [
        "requests==2.31.0",
        "opencv-python==4.8.1.78", 
        "numpy==1.24.3",
        "Pillow==10.0.1"
    ]
    
    for dep in dependencias:
        try:
            print(f"📦 Instalando {dep}...")
            subprocess.check_call([sys.executable, "-m", "pip", "install", dep])
            print(f"✅ {dep} instalado com sucesso!")
        except subprocess.CalledProcessError as e:
            print(f"❌ Erro ao instalar {dep}: {e}")
            return False
    
    print("\n🎉 Todas as dependências foram instaladas com sucesso!")
    print("🚀 Agora você pode executar: python sistema_integrado_completo.py")
    return True

if __name__ == "__main__":
    instalar_dependencias()
