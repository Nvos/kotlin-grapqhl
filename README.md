## Observability
### Prometheus (metrics)
#### Config
Append to **prometheus.yml**
```scrape_configs:
  # The job name is added as a label `job=<job_name>` to any timeseries scraped from this config.
  - job_name: 'spring'

    # metrics_path defaults to '/metrics'
    # scheme defaults to 'http'.
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8080']
```
### Jaeger (tracing)
### Grafana (metrics visualization)
## Testing graphql
Syntax for get in GraphqlResponse is from  https://github.com/json-path/JsonPath