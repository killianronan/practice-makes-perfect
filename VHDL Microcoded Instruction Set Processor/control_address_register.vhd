library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity control_address_register is
 Port ( 
       S,RESET ,Clk: in std_logic;
       op_in : in std_logic_vector(7 downto 0);
       CAR_out : out std_logic_vector(7 downto 0)
      );
end control_address_register;

architecture Behavioral of control_address_register is
    signal temp : std_logic_vector(7 downto 0);
begin
    process(Clk, reset)
    begin
        if(S = '1') then
            if(Clk = '1' )then CAR_out <= op_in after 5ns; end if;
        elsif(RESET = '1') then CAR_out <= x"E0" after 5ns;
        end if;
    end process;
end Behavioral;
