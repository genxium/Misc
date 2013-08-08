#include <linux/init.h>
#include <linux/module.h> /*Needed by all modules*/
#include <linux/kernel.h>

int init_module(void){
	printk(KERN_INFO "Hello World 1.\n");

	return 0;
}

void cleanup_module(void){
	printk(KERN_INFO "Goodbye World 1.\n");
}




