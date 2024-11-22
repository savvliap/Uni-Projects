library verilog;
use verilog.vl_types.all;
entity rr_arbiter_buggy_tb is
    generic(
        REQS            : integer := 4;
        BUG             : integer := 1
    );
    attribute mti_svvh_generic_type : integer;
    attribute mti_svvh_generic_type of REQS : constant is 1;
    attribute mti_svvh_generic_type of BUG : constant is 1;
end rr_arbiter_buggy_tb;
