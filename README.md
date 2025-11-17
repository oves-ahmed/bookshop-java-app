# Book Shop — Spring Boot + Docker + Kubernetes + Jenkins (Beginner Friendly)

A simple Book Shop CRUD app with a beautiful UI (Thymeleaf + CSS) to practice CI/CD, Docker and Kubernetes (AKS-compatible).

## 1) Run Locally

```bash
# build and run
mvn -DskipTests package
java -jar target/bookshop-0.0.1-SNAPSHOT.jar
# open http://localhost:8080
```

## 2) Build and Run with Docker

```bash
docker build -t bookshop:local .
docker run --rm -p 8080:8080 bookshop:local
# open http://localhost:8080
```

## 3) Push to Docker Hub

```bash
# tag and push
docker tag bookshop:local your-dockerhub-username/bookshop:latest
docker login
docker push your-dockerhub-username/bookshop:latest
```

## 4) Deploy to Kubernetes (minikube/kind/AKS)

```bash
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml

# Access (NodePort on 30080)
kubectl get svc -n bookshop
# open http://<node-ip>:30080
```

### Switch to AKS Notes
- Create AKS: `az aks create ...`
- Get credentials: `az aks get-credentials -g <rg> -n <aks-name>`
- Ensure image is in a registry AKS can pull (e.g., Docker Hub public or ACR).
- If using ACR, add `imagePullSecrets` and `kubectl create secret docker-registry`.

## 5) Jenkins Pipeline

- Create credentials:
  - `dockerhub-creds` (Username/Password)
  - `kubeconfig-file` (Secret file with kubeconfig)
- Create a Multibranch or Pipeline job pointing to this repo; Jenkinsfile provided.

Pipeline does:
1. Maven build & test
2. Build Docker image
3. Push to registry
4. `kubectl apply` K8s manifests

## 6) Project Structure

```
bookshop/
  Dockerfile
  Jenkinsfile
  pom.xml
  README.md
  k8s/
    configmap.yaml
    deployment.yaml
    namespace.yaml
    service.yaml
  src/
    main/
      java/com/example/bookshop/
        BookshopApplication.java
        controller/BookController.java
        model/Book.java
        repository/BookRepository.java
      resources/
        application.properties
        static/css/styles.css
        templates/
          form.html
          index.html
    test/java/com/example/bookshop/BookshopApplicationTests.java
```

## 7) Notes

- H2 in-memory is used for simplicity; data resets on restart.
- Change image name `your-dockerhub-username/bookshop:latest` in `Jenkinsfile` and `k8s/deployment.yaml`.
- Liveness/Readiness hit Spring Boot Actuator endpoints; if needed add actuator to `pom.xml`:
  - (Already included via web-starter’s health; for full actuator: add `spring-boot-starter-actuator` and management endpoints.)
```

# Add actuator dependency line
