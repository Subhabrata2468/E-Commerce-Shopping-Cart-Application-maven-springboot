#!/usr/bin/env bash
set -euo pipefail

# Ansible on Ubuntu 24.04 LTS (Noble)
# Two options:
#   - Ubuntu repo (quick, stable): apt install ansible
#   - Official PPA (newer): add-apt-repository ppa:ansible/ansible
# Default below uses the PPA for latest stable.

if [[ $EUID -ne 0 ]]; then
  echo "Please run as root: sudo $0"
  exit 1
fi

export DEBIAN_FRONTEND=noninteractive

echo "[Ansible] Updating apt and installing prerequisites..."
apt-get update -y
apt-get install -y software-properties-common

USE_PPA="${USE_PPA:-1}"

if [[ "$USE_PPA" == "1" ]]; then
  echo "[Ansible] Adding official Ansible PPA..."
  add-apt-repository --yes --update ppa:ansible/ansible
fi

echo "[Ansible] Installing Ansible..."
apt-get install -y ansible

echo "[Ansible] Version:"
ansible --version || true

# Optional: create a basic inventory file if not present
if [[ ! -f /etc/ansible/hosts ]]; then
  echo "[Ansible] Creating default inventory file at /etc/ansible/hosts..."
  install -d -m 0755 /etc/ansible
  bash -c 'cat > /etc/ansible/hosts <<EOF
[local]
localhost ansible_connection=local
EOF'
fi

echo "[Ansible] Done. Configure SSH keys for remote hosts with: ssh-keygen && ssh-copy-id user@host"