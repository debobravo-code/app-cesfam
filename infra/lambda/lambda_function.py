
import json
import logging

logger = logging.getLogger()
logger.setLevel(logging.INFO)


def lambda_handler(event, context):
    procesados = 0

    for record in event.get("Records", []):
        try:
            mensaje = json.loads(record["body"])

            funcionario = mensaje.get("funcionario", "No especificado")
            tipo = mensaje.get("tipo", "NOTIFICACION")
            detalle = mensaje.get("detalle", "Sin detalle")

            logger.info(
                "CESFAM | Tipo: %s | Funcionario: %s | Detalle: %s",
                tipo, funcionario, detalle
            )

            procesados += 1

        except Exception:
            logger.exception("Error al procesar notificacion")
            raise

    return {
        "statusCode": 200,
        "body": json.dumps({
            "mensaje": "Notificaciones procesadas correctamente",
            "cantidad": procesados
        })
    }
