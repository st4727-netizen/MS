# ========================================
# Script para taggear y hacer push de 5 microservicios
# ========================================

# Usuario de Docker Hub
$dockerUser = "isaaccappca"

# Lista de imágenes locales
$images = @(
    "ms-clientes-service",
    "ms-solicitud-service"
    "ms-correccion-datos-service",
    "ms-estado-solicitud-service",
    "ms-video-consentimiento-service",
)

# Recorre y procesa cada imagen
foreach ($img in $images) {

    $localTag = "$img:latest"
    $remoteTag = "$dockerUser/$img:latest"

    Write-Host "Tagging $localTag as $remoteTag ..."
    docker tag $localTag $remoteTag

    Write-Host "Pushing $remoteTag ..."
    docker push $remoteTag

    Write-Host "-------------------------------------------"
}

Write-Host "Proceso completado. Todas las imágenes fueron taggeadas y enviadas."
