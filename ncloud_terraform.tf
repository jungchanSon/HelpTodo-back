terraform {
  required_providers {
    ncloud = {
      source = "NaverCloudPlatform/ncloud"
    }
  }
}
provider "ncloud" {
  access_key = var.access_key
  secret_key = var.secret_key
  region = "KR"
  support_vpc = true
}

resource "ncloud_vpc" "todo-vpc" {
  name               = "todo-vpc"
  ipv4_cidr_block    = var.vpc_cidr_block
}

resource "ncloud_subnet" "todo-front" {
  network_acl_no = ncloud_vpc.todo-vpc.default_network_acl_no
  subnet         = var.frontend_subnet
  subnet_type    = "PUBLIC"
  vpc_no         = ncloud_vpc.todo-vpc.vpc_no
  zone           = "KR-2"
}

resource "ncloud_subnet" "todo-back" {
  network_acl_no = ncloud_vpc.todo-vpc.default_network_acl_no
  subnet         = var.backend_subnet
  subnet_type    = "PRIVATE"
  vpc_no         = ncloud_vpc.todo-vpc.vpc_no
  zone           = "KR-2"
}

resource "ncloud_server" "todo-back" {
  subnet_no = ncloud_subnet.todo-back.id
  name = "todoHelpBE"
  server_image_product_code = "SW.VSVR.OS.LNX64.CNTOS.0703.B050"
  server_product_code = "SW.VSVR.OS.LNX64.UBNTU.SVR2004.B050"
}

resource "ncloud_server" "todo-front" {
  subnet_no = ncloud_subnet.todo-front.id
  name = "todoHelpFE"
  server_image_product_code = "SW.VSVR.OS.LNX64.CNTOS.0703.B050"
  server_product_code = "SW.VSVR.OS.LNX64.UBNTU.SVR2004.B050 "
}
