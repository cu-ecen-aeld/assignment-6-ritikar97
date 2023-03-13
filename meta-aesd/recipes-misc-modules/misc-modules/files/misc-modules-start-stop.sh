
#!/bin/sh

case "$1" in
    start)
        echo "Starting misc-modules"
        /usr/bin/module_load faulty
        modprobe hello
        ;;
    stop)
        echo "Stopping misc-modules"
        /usr/bin/module_unload faulty
        rmmod hello
        ;;
    *)
        echo "Usage: $0 {start|stop}"
    exit 1
esac

exit 0