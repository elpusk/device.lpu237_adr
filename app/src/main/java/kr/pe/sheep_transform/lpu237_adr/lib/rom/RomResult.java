package kr.pe.sheep_transform.lpu237_adr.lib.rom;

public interface RomResult{
    int result_success = 0;
    int result_error = 1;
    int result_error_not_found = 2;
    int result_error_invalied_parameter = 3;
    int result_error_shorter_then_expected = 4;
    int result_error_not_loaded_dll = 5;
    int result_error_not_open_file = 6;
    int result_error_greater_then_expected = 7;
    int result_error_over_capacity = 8;
    int result_error_unknown_format = 9;//selected file is may be not rom file.
}
