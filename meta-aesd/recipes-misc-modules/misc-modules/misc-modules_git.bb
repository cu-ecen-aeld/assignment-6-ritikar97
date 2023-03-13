# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-ritikar97.git;protocol=ssh;branch=master \
           file://misc-modules-start-stop.sh"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "9132171a0cc110869587c59b38d0792ecdc50546"

S = "${WORKDIR}/git/misc-modules"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "misc-modules-start-stop.sh"
inherit update-rc.d

# TODO: Add the module load/unload application and any other files you need to install
# See https://git.yoctoproject.org/poky/plain/meta/conf/bitbake.conf?h=kirkstone
FILES:${PN} += "${bindir}/module_load"
FILES:${PN} += "${bindir}/module_unload"
FILES:${PN} += "${sysconfdir}/*"


do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
	# TODO: Install your binaries/scripts here.
	# Be sure to install the target directory with install -d first
	# Yocto variables ${D} and ${S} are useful here, which you can read about at 
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb
	
	install -d ${D}${bindir}
    install -d ${D}${base_libdir}/modules/5.15.91-yocto-standard/
    install -m 0755 ${S}/module_load ${D}${bindir}/	
    install -m 0755 ${S}/module_unload ${D}${bindir}/	
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/misc-modules-start-stop.sh ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/hello.ko ${D}${base_libdir}/modules/5.15.91-yocto-standard/
	install -m 0755 ${S}/faulty.ko ${D}${base_libdir}/modules/5.15.91-yocto-standard/
}
