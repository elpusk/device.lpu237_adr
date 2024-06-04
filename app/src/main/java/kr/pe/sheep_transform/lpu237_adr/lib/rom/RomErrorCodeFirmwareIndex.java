package kr.pe.sheep_transform.lpu237_adr.lib.rom;

public interface  RomErrorCodeFirmwareIndex{//this code must be less then zero.
    int error_firmware_index_none_matched_name = -1;
    int error_firmware_index_none_matched_version = -2;
    int error_firmware_index_none_device_name = -3;
    int error_firmware_index_none_device_version = -4;
    int error_firmware_index_format_device_version = -5;
    int error_firmware_index_none_file_header = -6;
}
