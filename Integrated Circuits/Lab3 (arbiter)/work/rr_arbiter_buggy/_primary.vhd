library verilog;
use verilog.vl_types.all;
entity rr_arbiter_buggy is
    generic(
        REQS            : integer := 4;
        BUG             : integer := 0
    );
    port(
        clk             : in     vl_logic;
        rst             : in     vl_logic;
        reqs_i          : in     vl_logic_vector;
        grants_o        : out    vl_logic_vector;
        any_grant_o     : out    vl_logic
    );
    attribute mti_svvh_generic_type : integer;
    attribute mti_svvh_generic_type of REQS : constant is 2;
    attribute mti_svvh_generic_type of BUG : constant is 2;
end rr_arbiter_buggy;
