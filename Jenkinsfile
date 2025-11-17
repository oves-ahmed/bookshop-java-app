pipeline {
  agent any
  environment {
    REGISTRY_CREDENTIALS = credentials('dockerhub-creds')   // create in Jenkins
    DOCKER_IMAGE = "your-dockerhub-username/bookshop:latest"
    KUBECONFIG_CREDENTIALS = credentials('kubeconfig-file') // create in Jenkins (secret file)
  }
  stages {
    stage('Checkout') {
      steps { checkout scm }
    }
    stage('Build & Test') {
      steps {
        sh 'mvn -v'
        sh 'mvn -B -DskipTests=false test package'
      }
      post {
        always { junit '**/target/surefire-reports/*.xml' }
      }
    }
    stage('Build Docker Image') {
      steps {
        sh 'docker build -t $DOCKER_IMAGE .'
      }
    }
    stage('Login & Push') {
      steps {
        sh 'echo $REGISTRY_CREDENTIALS_PSW | docker login -u $REGISTRY_CREDENTIALS_USR --password-stdin'
        sh 'docker push $DOCKER_IMAGE'
      }
    }
    stage('Deploy to Kubernetes') {
      steps {
        withCredentials([file(credentialsId: 'kubeconfig-file', variable: 'KUBECONFIG_FILE')]) {
          sh 'mkdir -p $WORKSPACE/.kube && cp $KUBECONFIG_FILE $WORKSPACE/.kube/config'
          sh 'export KUBECONFIG=$WORKSPACE/.kube/config && kubectl apply -f k8s/namespace.yaml && kubectl apply -f k8s/configmap.yaml && kubectl apply -f k8s/deployment.yaml && kubectl apply -f k8s/service.yaml'
        }
      }
    }
  }
  post {
    success { echo 'Deployment successful!' }
    failure { echo 'Pipeline failed.' }
  }
}
