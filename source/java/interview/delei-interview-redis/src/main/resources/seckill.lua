-- seckill key
local _key = KEYS[1]
-- 秒杀数量
local _amount = tonumber(ARGV[1])

-- 当前剩余库存
local current_stock = tonumber(redis.call('get',_key) or "0")

if current_stock<=0 then
-- 已无库存，返回
return -1
elseif (_amount>current_stock) then
-- 数量超过剩余库存
return 0
else
-- 减库存
redis.call("DECRBY",_key,_amount)
return 1
end