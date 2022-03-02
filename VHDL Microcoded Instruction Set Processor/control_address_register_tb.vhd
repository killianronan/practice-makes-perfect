----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 24.04.2020 12:02:47
-- Design Name: 
-- Module Name: control_address_register_tb - Behavioral
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

entity control_address_register_tb is
--  Port ( );
end control_address_register_tb;

architecture Behavioral of control_address_register_tb is

    component control_address_register is
        Port ( 
            S : in std_logic;
            op_in : in std_logic_vector(7 downto 0);
            reset, Clk : in std_logic;
            CAR_out : out std_logic_vector(7 downto 0)
        );
    end component;

    --Input    
    signal S : std_logic := '0';
    signal op_in : std_logic_vector(7 downto 0):= x"00";
    signal reset, Clk: std_logic := '0';    
    --Output
    signal CAR_out : std_logic_vector(7 downto 0):= x"00";
        
begin
    UUT: control_address_register
    Port Map(
        S => S,
        op_in => op_in,
        reset => reset,
        CLK => CLK,
        CAR_out => CAR_out
    );
simulation_process :process
begin
        --Initialize with reset to 0x00
        reset <= '1';
        wait for 15ns;
        reset <= '0';
        wait for 15ns;
        --Test with S set, should set result
        op_in <= x"66";
        S <= '1';
        Clk <= '1';
        wait for 15ns;
        
        --Test without S set, should do nothing
        op_in <= x"DE";
        S <= '0';
        Clk <= '1';
        wait for 15ns;
        Clk <= '0';
        wait for 15ns;  
        
        --Reset CAR_out
        reset <= '1';
        wait for 15ns;
    
     end process;

end Behavioral;
