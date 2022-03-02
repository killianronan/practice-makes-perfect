----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 24.04.2020 10:57:15
-- Design Name: 
-- Module Name: control_memory_tb - Behavioral
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

entity control_memory_tb is
--  Port ( );
end control_memory_tb;

architecture Behavioral of control_memory_tb is
    component control_memory is
        Port ( 
           CAR_in : in std_logic_vector(7 downto 0);
           NA : out  std_logic_vector(7 downto 0);
           MS : out  std_logic_vector(2 downto 0);
           FS : out std_logic_vector(4 downto 0);
           MC,IL,PI,PL,TD,TA,TB,MB,MD,RW,MM,MW : out std_logic
        );
    end component;
           signal CAR_in : std_logic_vector(7 downto 0) := x"00";
           signal NA : std_logic_vector(7 downto 0) := x"00";
           signal MS : std_logic_vector(2 downto 0):= "000";
           signal FS : std_logic_vector(4 downto 0) := "00000";
           signal MC,IL,PI,PL,TD,TA,TB,MB,MD,RW,MM,MW : std_logic := '0';
begin
   UUT: control_memory
    Port Map(
        CAR_in => CAR_in,
        NA => NA,
        FS => FS,
        RW => RW,
        IL => IL,
        PL => PL, 
        PI => PI,
        TD => TD,
        TA => TA,
        TB => TB,
        MS => MS,
        MC => MC,
        MB => MB,
        MD => MD,
        MM => MM,
        MW => MW
    );
    
sim_proc :process
begin
        CAR_in <= x"0000";
        wait for 10ns;
        CAR_in <= x"0001";
        wait for 10ns;
        CAR_in <= x"0002";
        wait for 10ns;
        CAR_in <= x"0003";
        wait for 10ns;
        CAR_in <= x"0004";
        wait for 10ns;
     end process;
end Behavioral;
