library verilog;
use verilog.vl_types.all;
entity counter4bit is
    port(
        clk             : in     vl_logic;
        reset           : in     vl_logic;
        satEn           : in     vl_logic;
        countout        : out    vl_logic_vector(3 downto 0)
    );
end counter4bit;
