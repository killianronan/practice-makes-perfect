library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
entity memory_M is
    Port ( 
            addr_in, data: in std_logic_vector(15 downto 0);
            MW, Clk : in std_logic;
            result : out std_logic_vector(15 downto 0)
    );end memory_M;

architecture Behavioral of memory_M is
type mem_array is array(0 to 511) of std_logic_vector(15 downto 0);
signal temp : unsigned(15 downto 0);
begin
sim_proc: process(data,addr_in)
  variable data_mem : mem_array := (
    
    x"0000", 
    x"F0F0", 
    x"0045",				
    x"0080",			
    x"00C0", 
    x"0100",
    x"0140", 
    x"0180",
    x"01C0",
    x"0200", 
    x"0001",
    x"02C0", 
    x"ABCD", 
    x"0400", 
    x"0400",  
    x"0400",
    x"0619",  
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",-- 16 --> 32

    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",
    x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000",x"0000"
);
    variable addr:integer;

    begin 
        addr:=conv_integer(addr_in(8 downto 0));
        if MW = '1'  then
            data_mem(addr):= data;
        end if;
         result <= data_mem(addr) after 5 ns;
end process;
end Behavioral;
