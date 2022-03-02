----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 24.04.2020 10:06:29
-- Design Name: 
-- Module Name: memory_M_tb - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity memory_M_tb is
--  Port ( );
end memory_M_tb;

architecture Behavioral of memory_M_tb is
    -- declare component to test
    component memory_M is
        Port ( 
            addr_in,data : in std_logic_vector(15 downto 0);
            Clk,MW : in std_logic;
            result : out std_logic_vector(15 downto 0)
        );
    end component;
    --Input
    signal addr_in, data : std_logic_vector(15 downto 0) := x"0000";
    signal Clk,MW : std_logic := '0';
    
    --Output
    signal result : std_logic_vector(15 downto 0):= x"0000";
        
begin
    UUT: memory_M
    Port Map(
        addr_in => addr_in,
        data => data,
        Clk => Clk,
        MW => MW,
        result => result
    );
        
sim_proc :process
begin
    --Read from addr 0x0000
    addr_in <= x"0001";
    MW <= '0';
    wait for 15ns;
    
    --Write to address 0x0400
    data <= x"1234";
    addr_in <= x"0400";
    MW <= '1';
    wait for 15ns;
    --Write to address 0x0500
    data <= x"5678";
    addr_in <= x"0500";
    MW <= '1';
    wait for 15ns;
    
    --Read from addr 0x0400
    addr_in <= x"0400";
    MW <= '0';
    wait for 15ns;

    --Read from addr 0x0500
    addr_in <= x"0500";
    MW <= '0';
    wait for 15ns;
   
end process;

end Behavioral;
