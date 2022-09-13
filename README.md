# kt-spring-boot-micrometer

## aop-timed

```
GET http://localhost:8081/timed/hello
GET http://localhost:8081/actuator/prometheus

# HELP smth_time_seconds Time taken to return smth
# TYPE smth_time_seconds summary
smth_time_seconds{class="com.example.timedannotation.DemoService",exception="none",method="doSmth",type="aop-timed",quantile="0.5",} 0.654311424
smth_time_seconds{class="com.example.timedannotation.DemoService",exception="none",method="doSmth",type="aop-timed",quantile="0.95",} 0.889192448
smth_time_seconds_count{class="com.example.timedannotation.DemoService",exception="none",method="doSmth",type="aop-timed",} 10.0
smth_time_seconds_sum{class="com.example.timedannotation.DemoService",exception="none",method="doSmth",type="aop-timed",} 6.964222125
# HELP smth_time_seconds_max Time taken to return smth
# TYPE smth_time_seconds_max gauge
smth_time_seconds_max{class="com.example.timedannotation.DemoService",exception="none",method="doSmth",type="aop-timed",} 0.892753916

```



## counter-timer-gauge

```
GET http://localhost:8082/demo/hello
GET http://localhost:8082/actuator/prometheus

# HELP app_request_start_with_a The number of requests start with A letter
# TYPE app_request_start_with_a gauge
app_request_start_with_a 2.0

# HELP app_demo_counter_demo_total  
# TYPE app_demo_counter_demo_total counter
app_demo_counter_demo_total{type="FAILED",} 0.0
app_demo_counter_demo_total{type="TOTAL",} 10.0
app_demo_counter_demo_total{type="PROCESSED",} 10.0

# HELP app_smth_timer_seconds_max  
# TYPE app_smth_timer_seconds_max gauge
app_smth_timer_seconds_max{type="method-timer",} 0.052
# HELP app_smth_timer_seconds  
# TYPE app_smth_timer_seconds summary
app_smth_timer_seconds{type="method-timer",quantile="0.5",} 0.025952256
app_smth_timer_seconds{type="method-timer",quantile="0.95",} 0.052166656
app_smth_timer_seconds_count{type="method-timer",} 10.0
app_smth_timer_seconds_sum{type="method-timer",} 0.276
```


