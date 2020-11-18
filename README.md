# Java Microservice Example with Helm Chart

A microservice app created with Spring Boot `2.4.0` which accesses a PostgreSQL database and exposes a REST API.

## Helm Chart

`k8s/charts/simple-service` is the Helm chart to install this microservice.

Below are some key points of this Helm chart.

Spring Boot Actuator is used to expose endpoints for liveness and readiness probes.

```yaml
ports:
  - name: http
    containerPort: 8080
    protocol: TCP
livenessProbe:
  httpGet:
    path: /actuator/health/liveness
    port: http
  failureThreshold: 3
readinessProbe:
  httpGet:
    path: /actuator/health/readiness
    port: http
  failureThreshold: 3
```

Database configurations are passed using environment variables. These environment variables will be used by Spring, see `application.yaml` file.

```yaml
env:
  - name: DB_HOST
    value: {{ .Values.app.db.host | quote }}
  - name: DB_PORT
    value: {{ .Values.app.db.port | quote }}
  - name: DB_USER
    value: {{ .Values.app.db.username | quote }}
  - name: DB_PASSWORD
    value: {{ .Values.app.db.password | quote }}
  - name: DB_NAME
    value: {{ .Values.app.db.database | quote }}
```

## Helmfile

The `k8s/helmfile.yaml` file is used to install microservice and PostgreSQL.

The `k8s/dev.yaml` file contains the configuration values for the default environment.

In the `helmfile.yaml`, configurations like `postgres.username` are passed to the microservice and PostgreSQL.

## How to Use

1. Start Minikube.

2. Use `eval $(minikube docker-env)` to configure Docker client in the current terminal to use Docker runtime in Minikube.

3. Build container image using `mvn package`.

4. Go to `k8s` directory and run `helmfile apply` to install service and PostgreSQL.