defmodule RestEndpoint.Config do

  alias RestEndpoint.Http

  def get_calculator_address() do
    eureka = Application.get_env(:rest_endpoint, :eureka)
    calculator = Application.get_env(:rest_endpoint, :calculator_name)
    headers = [{"Content-Type", "application/json"}, {"Accept", "application/json"}]
    response = Http.get("#{eureka}/apps/#{calculator}", headers)
    instances = response["application"]["instance"]
    instance = Enum.random(instances)
    "http://#{instance["ipAddr"]}:#{instance["port"]["$"]}" 
  end
end
