# Raul Esteban Alzate whatsapp -> 313 515 86 11.


#se notifica inicio de instalacion
echo "iniciando instalacion ..."

# En caso de haber una instalacion previa se elimina TentactilPOS
sudo rm -r '/home/TentactilPOS'

# se copia la carpeta dist al destino de instalacion home
sudo cp -r 'dist' '/home/TentactilPOS'

# se notifica fin de la instalacion
echo "La instalacion a finalizado exitosamente."

