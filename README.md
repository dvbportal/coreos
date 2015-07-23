# CoreOS
CoreOS cloud configuration for xhyve

CoreOS is a simple OS based on ChromeOS by Google. It is a read-only OS that executes 
Docker containers. Due to the read-only system partition, all configuration takes place via
the cloud-init file in this repository.

## Starting CoreOS

CoreOS runs on OS X using the built-in hypervisor using xhyve. To start CoreOS run:

    sudo ./boot -fcloud.conf coreos
    
The output should look like this:
    ...    
             Starting Network Name Resolution...
    [  OK  ] Started Network Name Resolution.
    [  OK  ] Started Install an ssh key from /proc/cmdline.
    [  OK  ] Started Generate sshd host keys.
    [  OK  ] Reached target Multi-User System.
    [  OK  ] Listening on Docker Socket for the API.
             Mounting Mount ephemeral to /var/lib/docker...
    [    8.683778] EXT4-fs (vda): mounted filesystem with ordered data mode. Opts: (null)
    [  OK  ] Mounted Mount ephemeral to /var/lib/docker.
    [  OK  ] Started Load cloud-config from url defined in /proc/cmdline.
    [  OK  ] Reached target Load user-provided cloud configs.
    
    
    This is localhost (Linux x86_64 4.0.5) 09:10:25
    SSH host key: 6b:ad:d8:54:50:e3:22:08:c6:4f:3b:43:39:b4:64:c6 (DSA)
    SSH host key: d1:73:75:0f:6b:a8:29:d3:19:30:a8:d4:5d:bc:e5:4b (ED25519)
    SSH host key: b1:f8:ce:8c:11:b8:a4:ab:4d:34:32:77:9d:5f:14:02 (RSA)
    eth0: 192.168.64.32 fe80::a4c8:74ff:fe66:cf32
    
    localhost login: core (automatic login)
    
    CoreOS stable (709.0.0)
    Update Strategy: No Reboots
    Last login: Thu Jul 23 09:10:25 +0000 2015 on /dev/tty1.
    core@localhost ~ $
