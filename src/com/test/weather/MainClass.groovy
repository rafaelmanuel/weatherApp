package com.test.weather

import java.awt.BorderLayout
import java.awt.GridLayout
import java.awt.event.ActionListener
import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.JOptionPane
import groovy.swing.SwingBuilder

@Grab('com.github.groovy-wslite:groovy-wslite:1.1.2')
import wslite.soap.*

class MainClass {


	static main(args) {
		SwingBuilder sb = new SwingBuilder()
		sb.edt{
			def changes
			def currentIndex = 0
			experimentLayout = new GridLayout(0,2)
			panel = new JPanel()
			panelTop = new JPanel()
			panelCenter = new JPanel()
			panelBottom = new JPanel()

			frame = new JFrame(title:"Weather App" ,
			defaultCloseOperation:JFrame.EXIT_ON_CLOSE ,
			size:[420, 400],
			locationRelativeTo:null)

			//component
			lblZip = new JLabel("Zip :")
			txtZip = new JTextField(columns:7)
			cboZip = new JComboBox()
			btnOk = new JButton(text:"Ok")

			//component on center
			lblState = new JLabel(text:"State : ")
			lblCity = new JLabel(text:"City : ")
			lblForeCast = new JLabel(text:"Forecast :")
			lblFcDate = new JLabel("\t\t\t\tDate :")
			lblFcWeatherID = new JLabel("\t\t\t\tWeatherID :")
			lblFcDesciption = new JLabel("\t\t\t\tDesciption :")
			lblFcTemperature = new JLabel("\t\t\t\tTemperature :")
			lblMorningLow = new JLabel("\t\t\t\t\t- MorningLow :")
			lblDaytimeHigh = new JLabel("\t\t\t\t\t- DaytimeHigh :")
			lblPrecipNighttime = new JLabel("\t\t\t\t\t- PrecipNighttime :")
			lblPrecipDaytime = new JLabel("\t\t\t\t\t- PrecipDaytime :")


			//component on bottom
			btnPrev = new JButton(text:"Prev")
			btnNext = new JButton(text:"Next")

			//set component
			panel = frame.contentPane
			panel.setLayout(new BorderLayout())
			panelTop.setLayout(flowLayout())
			panelCenter.setLayout(new BoxLayout(panelCenter , BoxLayout.Y_AXIS))
			panelCenter.setBorder(BorderFactory.createEmptyBorder(10,10,10,10))
			panelBottom.setLayout(flowLayout())

			//add item and event
			cboZip.addItem("GetCityForecastByZIP")
			cboZip.addItem("GetCityWeatherByZIP")
			btnOk.addActionListener(changes = {
				currentIndex = 0
				if(cboZip.getSelectedIndex() == 0){
					def temp = ""
					def forecastList = []
					def prevOrNext = ""
					
					def response = new MainClass().getCityForecastByZIPResponse(txtZip.text)
					if(response.Success.equals("true")){
						response.ForecastResult.Forecast.each{
							forecastList.add(new Forecast(
									date:it.Date,
									weatherId:it.WeatherID,
									desciption:it.Desciption,
									morningLow: it.Temperatures.MorningLow,
									daytimeHigh: it.Temperatures.DaytimeHigh,
									precipNighttime: it.ProbabilityOfPrecipiation.Nighttime,
									precipDaytime: it.ProbabilityOfPrecipiation.Daytime
									))
						}

						temp = lblState.text
						lblState.text = temp.split(":")[0] + ": "

						temp = lblCity.text
						lblCity.text = temp.split(":")[0] + ": "


						temp = lblFcDate.text
						lblFcDate.text = temp.split(":")[0] + ": "

						temp = lblFcWeatherID.text
						lblFcWeatherID.text = temp.split(":")[0] + ": "

						temp = lblFcDesciption.text
						lblFcDesciption.text = temp.split(":")[0] + ": "

						temp = lblMorningLow.text
						lblMorningLow.text = temp.split(":")[0] + ": "

						temp = lblDaytimeHigh.text
						lblDaytimeHigh.text = temp.split(":")[0] + ": "

						temp = lblPrecipNighttime.text
						lblPrecipNighttime.text = temp.split(":")[0] + ": "

						temp = lblPrecipDaytime.text
						lblPrecipDaytime.text = temp.split(":")[0] + ": "


						lblState.text += response.State
						lblCity.text += response.City
						lblFcDate.text += forecastList[currentIndex].getDate()
						lblFcWeatherID.text += forecastList[currentIndex].getWeatherId()
						lblFcDesciption.text += forecastList[currentIndex].getDesciption()
						lblMorningLow.text += forecastList[currentIndex].getMorningLow()
						lblDaytimeHigh.text += forecastList[currentIndex].getDaytimeHigh()
						lblPrecipNighttime.text += forecastList[currentIndex].getPrecipNighttime()
						lblPrecipDaytime.text += forecastList[currentIndex].getPrecipDaytime()


						println currentIndex
					}else
						JOptionPane.showMessageDialog(frame , "Invalid Zip")

				}

			} as ActionListener)

			btnNext.addActionListener({
				def forecastList = []
				def response = new MainClass().getCityForecastByZIPResponse(txtZip.text)
				response.ForecastResult.Forecast.each{
					forecastList.add(new Forecast(
							date:it.Date,
							weatherId:it.WeatherID,
							desciption:it.Desciption,
							morningLow: it.Temperatures.MorningLow,
							daytimeHigh: it.Temperatures.DaytimeHigh,
							precipNighttime: it.ProbabilityOfPrecipiation.Nighttime,
							precipDaytime: it.ProbabilityOfPrecipiation.Daytime
							))
				}

				if(currentIndex < forecastList.size() - 1){
					currentIndex ++
					def temp = ""
					temp = lblState.text
					lblState.text = temp.split(":")[0] + ": "

					temp = lblCity.text
					lblCity.text = temp.split(":")[0] + ": "


					temp = lblFcDate.text
					lblFcDate.text = temp.split(":")[0] + ": "

					temp = lblFcWeatherID.text
					lblFcWeatherID.text = temp.split(":")[0] + ": "

					temp = lblFcDesciption.text
					lblFcDesciption.text = temp.split(":")[0] + ": "

					temp = lblMorningLow.text
					lblMorningLow.text = temp.split(":")[0] + ": "

					temp = lblDaytimeHigh.text
					lblDaytimeHigh.text = temp.split(":")[0] + ": "

					temp = lblPrecipNighttime.text
					lblPrecipNighttime.text = temp.split(":")[0] + ": "

					temp = lblPrecipDaytime.text
					lblPrecipDaytime.text = temp.split(":")[0] + ": "


					lblState.text += response.State
					lblCity.text += response.City
					lblFcDate.text += forecastList[currentIndex].getDate()
					lblFcWeatherID.text += forecastList[currentIndex].getWeatherId()
					lblFcDesciption.text += forecastList[currentIndex].getDesciption()
					lblMorningLow.text += forecastList[currentIndex].getMorningLow()
					lblDaytimeHigh.text += forecastList[currentIndex].getDaytimeHigh()
					lblPrecipNighttime.text += forecastList[currentIndex].getPrecipNighttime()
					lblPrecipDaytime.text += forecastList[currentIndex].getPrecipDaytime()
					println currentIndex
				}

			} as ActionListener)

			btnPrev.addActionListener({
				def forecastList = []
				def response = new MainClass().getCityForecastByZIPResponse(txtZip.text)
				response.ForecastResult.Forecast.each{
					forecastList.add(new Forecast(
							date:it.Date,
							weatherId:it.WeatherID,
							desciption:it.Desciption,
							morningLow: it.Temperatures.MorningLow,
							daytimeHigh: it.Temperatures.DaytimeHigh,
							precipNighttime: it.ProbabilityOfPrecipiation.Nighttime,
							precipDaytime: it.ProbabilityOfPrecipiation.Daytime
							))
				}

				if(currentIndex > 0){
					currentIndex --
					def temp = ""

					temp = lblState.text
					lblState.text = temp.split(":")[0] + ": "

					temp = lblCity.text
					lblCity.text = temp.split(":")[0] + ": "


					temp = lblFcDate.text
					lblFcDate.text = temp.split(":")[0] + ": "

					temp = lblFcWeatherID.text
					lblFcWeatherID.text = temp.split(":")[0] + ": "

					temp = lblFcDesciption.text
					lblFcDesciption.text = temp.split(":")[0] + ": "

					temp = lblMorningLow.text
					lblMorningLow.text = temp.split(":")[0] + ": "

					temp = lblDaytimeHigh.text
					lblDaytimeHigh.text = temp.split(":")[0] + ": "

					temp = lblPrecipNighttime.text
					lblPrecipNighttime.text = temp.split(":")[0] + ": "

					temp = lblPrecipDaytime.text
					lblPrecipDaytime.text = temp.split(":")[0] + ": "


					lblState.text += response.State
					lblCity.text += response.City
					lblFcDate.text += forecastList[currentIndex].getDate()
					lblFcWeatherID.text += forecastList[currentIndex].getWeatherId()
					lblFcDesciption.text += forecastList[currentIndex].getDesciption()
					lblMorningLow.text += forecastList[currentIndex].getMorningLow()
					lblDaytimeHigh.text += forecastList[currentIndex].getDaytimeHigh()
					lblPrecipNighttime.text += forecastList[currentIndex].getPrecipNighttime()
					lblPrecipDaytime.text += forecastList[currentIndex].getPrecipDaytime()
					println currentIndex
				}

			} as ActionListener)

			//add to panel
			panel.add(panelTop , BorderLayout.NORTH)
			panel.add(panelCenter , BorderLayout.CENTER)
			panel.add(panelBottom , BorderLayout.SOUTH)

			//add component to panelTop
			panelTop.add(lblZip)
			panelTop.add(txtZip)
			panelTop.add(cboZip)
			panelTop.add(btnOk)

			//add component to panelCenter
			panelCenter.add(lblState)
			panelCenter.add(lblCity)
			panelCenter.add(new JLabel("------------------------------------------------"))
			panelCenter.add(lblForeCast)
			panelCenter.add(lblFcDate)
			panelCenter.add(lblFcWeatherID)
			panelCenter.add(lblFcDesciption)
			panelCenter.add(new JLabel("\t\t\t\t\t----------------------------------------------"))
			panelCenter.add(lblFcTemperature)
			panelCenter.add(lblMorningLow)
			panelCenter.add(lblDaytimeHigh)
			panelCenter.add(new JLabel("\t\t\t\t\t----------------------------------------------"))
			panelCenter.add(new JLabel("\t\t\t\tProbabilityOfPrecipiation"))
			panelCenter.add(lblPrecipNighttime)
			panelCenter.add(lblPrecipDaytime)

			//add component to panelBottom
			panelBottom.add(btnPrev)
			panelBottom.add(btnNext)


			frame.show()
		}
	}

	def getCityForecastByZIPResponse(def zip){
		def client = new SOAPClient('http://wsf.cdyne.com/WeatherWS/Weather.asmx')
		def response = client.send("""
			<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:weat="http://ws.cdyne.com/WeatherWS/">
			<soapenv:Header/>
			<soapenv:Body>
			<weat:GetCityForecastByZIP>
         	<!--Optional:-->
         	<weat:ZIP>${zip}</weat:ZIP>
			</weat:GetCityForecastByZIP>
			</soapenv:Body>
			</soapenv:Envelope>
			""")
		response.GetCityForecastByZIPResponse.GetCityForecastByZIPResult
	}

}


class Forecast {
	def date
	def weatherId
	def desciption
	def morningLow
	def daytimeHigh
	def precipNighttime
	def precipDaytime

}
