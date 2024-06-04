package kr.pe.sheep_transform.lpu237_adr.lib.mgmt;

public enum MgmtTypeRequest {
    Request_none,
    Request_kill,
    Request_update_list,
    Request_get_info_for_list,
    Request_get_uid,
    Request_get_parameters,
    Request_set_parameters,
    Request_start_bootloader,
    Request_firmware_sector_info,
    Request_firmware_erase,
    Request_firmware_write,
    Request_run_app,
    Request_recover_parameters
}
