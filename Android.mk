LOCAL_PATH := $(call my-dir)

ifeq ($(BOARD_VENDOR),samsung)
ifeq ($(BOARD_FAMILY),celox)
ifeq ($(TARGET_BOARD_PLATFORM),msm8660)
    include $(call all-subdir-makefiles,$(LOCAL_PATH))
endif
endif
endif
