#!/bin/bash

if [ -z $1 ]; then
    echo "usage: ./$0 [-fconfig] <vm>"
    echo "examples: ./$0 ubuntu"
    echo "          ./$0 -fcloud.conf coreos"
fi

while getopts ":f:" opt; do
  case ${opt} in
    f)
      [[ -r "${OPTARG}" ]] && source "${OPTARG}" || \
        echo "Warning: couldn't read \"${OPTARG}\". Ignoring." >&2
      shift
      ;;
    \?)
      echo "Warning: unsupported option '-${OPTARG}'. Ignoring." >&2
      ;;
    :)
      echo "Warning: no cloud-config provided. Ignoring." >&2
    esac
done

VM=$1
MEM="-m 1G"
#SMP="-c 2"
NET="-s 2:0,virtio-net"
IMG_CD="-s 3,ahci-cd,$VM/cd.img"
IMG_HDD="-s 4,virtio-blk,$VM/hdd.img"
CLOUD_CONFIG_DEF=https://raw.githubusercontent.com/coreos/coreos-xhyve/master/cloud-init/docker-only.txt
CLOUD_CONFIG=${CLOUD_CONFIG:-${CLOUD_CONFIG_DEF}}
[[ -n "${SSHKEY}" ]] && SSHKEY="sshkey=\"${SSHKEY}\""

if [ $VM == "install" ]; then
    KERNEL="$VM/vmlinuz"
    INITRD="$VM/initrd.gz"
    CMDLINE="earlyprintk=serial console=ttyS0"
fi

if [ $VM == "ubuntu" ]; then
    KERNEL="$VM/boot/vmlinuz-3.16.0-30-generic"
    INITRD="$VM/boot/initrd.img-3.16.0-30-generic"
    CMDLINE="earlyprintk=serial console=ttyS0 root=/dev/vda1 ro"
fi

if [ $VM == "coreos" ]; then
    KERNEL="$VM/coreos_production_pxe.vmlinuz"
    INITRD="$VM/coreos_production_pxe_image.cpio.gz"
    CMDLINE="earlyprintk=serial console=ttyS0 ${SSHKEY} coreos.autologin"
    CMDLINE="${CMDLINE} cloud-config-url=${CLOUD_CONFIG}"
fi

if [ ! -z $2 ]; then
    IMG_HDD2="-s 5,virtio-blk,$2"
    echo "Adding disk $2"
fi

PCI_DEV="-s 0:0,hostbridge -s 31,lpc"
LPC_DEV="-l com1,stdio"
UUID="-U FC525796-05F5-420B-AFE1-7E90BFC150BE"

build/xhyve  -A $MEM $SMP $PCI_DEV $LPC_DEV $NET $IMG_CD $IMG_HDD $IMG_HDD2 $UUID -f kexec,$KERNEL,$INITRD,"$CMDLINE"
