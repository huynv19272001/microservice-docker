# Microservice


### SERVER K8s


+ 10.36.126.90
+ 10.36.126.91

+ 10.36.126.95
+ 10.36.126.96
+ 10.36.126.106


+ 10.16.186.12 - rancher / docker-registry:35443
+ 10.16.186.13 - k8s-master-1
+ 10.16.186.14 - k8s-master-2
+ 10.16.186.15 - k8s-worker-1
+ 10.16.186.16 - k8s-worker-2
+ 10.16.186.17 - k8s-worker-3
+ 10.16.186.11 - k8s-worker-4

### SERVER ELK
`DEV`: root / Lpb!@#123
+ 10.36.126.15


+ 10.16.186.18 - es-master-01
+ 10.16.186.19 - es-node-01
+ 10.16.186.20 - es-node-02
+ 10.16.186.21 - logstash01
+ 10.16.186.22 - logstash02


### Tracing error
`DEV`: root / Lpb!@#123
+ 10.36.126.135 - Kafka / MongoDB / WebApp


+ 10.16.185.32 - kafka
+ 10.16.185.33 - kafka
+ 10.16.185.34 - kafka
+ 10.16.193.56 - MongoDB
+ 10.16.19.26 - WebApp

- 10.16.186.11   ESB_Microservice_HAProxy
- 10.16.186.13   ESB_Microservice_Master_01
- 10.16.186.14   ESB_Microservice_Master_02
10.16.186.15   ESB_Microservice_worker_01
10.16.186.16   ESB_Microservice_worker_02
10.16.186.17   ESB_Microservice_worker_03

kubeadm join vip-k8s-master:8443 --token lq9xzi.8qzn8mpk5ztmacb2 \
        --discovery-token-ca-cert-hash sha256:f0c1aa7ccd4fa84c94f2f02a03d9c4f2415fb7517c65e217ea421f3de9a4e028 \
        --control-plane --certificate-key 5cc8b02880cbc4fa620e7f74d33022263ea28a3d1d8f0addd985ff47b69a0942


kubeadm join vip-k8s-master:8443 --token lq9xzi.8qzn8mpk5ztmacb2 --discovery-token-ca-cert-hash sha256:f0c1aa7ccd4fa84c94f2f02a03d9c4f2415fb7517c65e217ea421f3de9a4e028


https://www.linuxtechi.com/setup-highly-available-kubernetes-cluster-kubeadm/

https://www.learnitguide.net/2018/07/create-your-own-private-docker-registry.html

