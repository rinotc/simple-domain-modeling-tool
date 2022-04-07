# Cluster
resource "aws_ecs_cluster" "sdmt-cluster" {
  name = "sdmt-cluster"
}

resource "aws_ecs_service" "sdmt" {
  name                               = "sdmt"
  cluster                            = aws_ecs_cluster.sdmt-cluster.id
  deployment_minimum_healthy_percent = 100
  deployment_maximum_percent         = 200
  desired_count                      = var.aws_ecs_service_desired_count

  lifecycle {
    ignore_changes = [
      desired_count,
      task_definition,
    ]
  }

  network_configuration {
    subnets = []
  }
}