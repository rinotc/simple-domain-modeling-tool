variable "aws_region" {
  description = "The AWS region to create things in."
  default = "ap-northeast-1"
}

variable "az_count" {
  description = "Number of AZs to covert in given AWS region"
  default = "1"
}

